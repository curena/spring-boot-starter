package org.cecil.auth

import java.nio.charset.Charset

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AuthenticationControllerIntegrationSpec extends BaseIntegrationSpec {
    def "GET /user unauthenticated user gets 401"() {
        expect:
        mockMvc.perform(get('/user')).andExpect(status().isUnauthorized())
    }

    def "GET /user authenticated user gets user name response"() {
        expect:
        def httpResponse = mockMvc.perform(get('/user')).andExpect(status().isOk()).andReturn()
        def name = httpResponse.getResponse().getContentAsString(Charset.defaultCharset())
        name == "foo"
    }
}