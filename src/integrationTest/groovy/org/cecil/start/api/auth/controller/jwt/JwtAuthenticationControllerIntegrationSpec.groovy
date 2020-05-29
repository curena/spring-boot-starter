package org.cecil.start.api.auth.controller.jwt


import org.cecil.start.BaseWebMvcIntegrationSpec
import org.cecil.start.api.auth.jwt.model.JwtTokenRequest
import org.cecil.start.api.auth.jwt.model.JwtTokenResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestPropertySource(locations = "/application-test.yml")
class JwtAuthenticationControllerIntegrationSpec extends BaseWebMvcIntegrationSpec {
    @Value('${jwt.get-token-uri}')
    String getTokenUri

    @Value('${jwt.refresh-token-uri}')
    String refreshTokenUri

    @Unroll
    def "POST to #getTokenUri with #description should return #expected"() {
        expect:
        def response = mockMvc.perform(
                post(getTokenUri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().is(expected))
                .andReturn().getResponse().getContentAsString()

        if (expected == 200) {
            def authenticationResponse = objectMapper.readValue(response, JwtTokenResponse)
            assert authenticationResponse.token
            assert !authenticationResponse.token.isEmpty()
        }

        where:
        requestBody                       | description            | expected
        new JwtTokenRequest("foo", "bar") | "valid request body"   | HttpStatus.OK.value()
        new JwtTokenRequest("bar", "baz") | "invalid request body" | HttpStatus.UNAUTHORIZED.value()
    }
}