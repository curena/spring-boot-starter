package org.cecil.start.api.auth.controller.jwt

import com.auth0.jwt.exceptions.TokenExpiredException
import org.cecil.start.BaseWebMvcIntegrationSpec
import org.cecil.start.api.auth.jwt.model.UserModel

import org.cecil.start.service.auth.JwtUserDetails
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import spock.lang.Ignore
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Ignore
@TestPropertySource(locations = "/application-test.yml")
class JwtAuthenticationControllerIntegrationSpec extends BaseWebMvcIntegrationSpec {
    @Value('${jwt.get-token-uri}')
    String getTokenUri

    @Value('${jwt.refresh-token-uri}')
    String refreshTokenUri

    @Value('${jwt.http-request-header}')
    String authorizationHeader

    @Value('${jwt.token.expiration-in-seconds}')
    long tokenExpiration

    def cleanup() {
        jwtTokenUtil.expirationTimeInSeconds = tokenExpiration
    }

    @Unroll
    def "POST to /authenticate with #description should return #expected"() {
        expect:
        def response = mockMvc.perform(
                post(getTokenUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(expected))
                .andReturn().getResponse().getContentAsString()

        if (expected == HttpStatus.OK.value()) {
            def authenticationResponse = objectMapper.readValue(response, JwtTokenResponse)
            assert authenticationResponse.token
            assert !authenticationResponse.token.isEmpty()
        }

        where:
        requestBody                       | description            | expected
        new UserModel("foo", "bar") | "valid request body"   | HttpStatus.OK.value()
        new UserModel("bar", "baz") | "invalid request body" | HttpStatus.UNAUTHORIZED.value()
    }

    def "GET to /refresh with expired token should return 400 and throw ExpiredJwtException"() {
        when:
        jwtTokenUtil.expirationTimeInSeconds = 1
        def expiredToken = jwtTokenUtil.generateToken("foo")
        sleep(1001)
        mockMvc.perform(
                get(refreshTokenUri)
                        .header(authorizationHeader, "Bearer ${expiredToken}"))
                .andExpect(status().isBadRequest())

        then:
        thrown(TokenExpiredException)
    }

    def "GET to /refresh with extant token should return 200"() {
        given:
        def validToken = jwtTokenUtil.generateToken(JwtUserDetails.builder()
                .username("foo")
                .password("bar")
                .authorities("USER").build())
        when:
        def response = mockMvc.perform(
                get(refreshTokenUri)
                        .header(authorizationHeader, "Bearer ${validToken}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        then:
        def tokenResponse = objectMapper.readValue(response, JwtTokenResponse)
        tokenResponse.token
        !tokenResponse.token.isEmpty()
    }
}