package org.cecil.start.api.auth.controller.jwt

import com.auth0.jwt.exceptions.TokenExpiredException
import org.cecil.start.BaseWebMvcIntegrationSpec
import org.cecil.start.api.auth.jwt.model.JwtTokenResponse
import org.cecil.start.api.auth.jwt.model.UserModel
import org.cecil.start.service.auth.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import spock.lang.Unroll

import static org.cecil.start.util.Constants.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestPropertySource(locations = "/application-test.yml")
class JwtAuthenticationIntegrationSpec extends BaseWebMvcIntegrationSpec {
    @Value('${jwt.token.expiration-in-seconds}')
    long tokenExpiration

    @Autowired
    UserService userService

    def cleanup() {
        jwtTokenUtil.expirationTimeInSeconds = tokenExpiration
    }

    @Unroll
    def "POST to /login with #description should return #expected"() {
        given:
        userService.signUpUser(new UserModel("foo", "bar"))

        expect:
        def response = mockMvc.perform(
                post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(expected))
                .andReturn().getResponse()


        if (expected == HttpStatus.OK.value()) {
            def authHeader = response.getHeader("Authorization")
            assert authHeader != null
            assert authHeader.contains("Bearer ")
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
                get(REFRESH_TOKEN_URL)
                        .header(AUTH_HEADER, "${TOKEN_PREFIX}${expiredToken}"))
                .andExpect(status().isBadRequest())

        then:
        thrown(TokenExpiredException)
    }

    def "GET to /refresh with extant token should return 200"() {
        given:
        def validToken = jwtTokenUtil.generateToken("foo")
        when:
        def response = mockMvc.perform(
                get(REFRESH_TOKEN_URL)
                        .header(AUTH_HEADER, "${TOKEN_PREFIX}${validToken}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString()

        then:
        def tokenResponse = objectMapper.readValue(response, JwtTokenResponse)
        tokenResponse.token
        !tokenResponse.token.isEmpty()
    }
}