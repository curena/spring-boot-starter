package org.cecil.start.api.auth.controller.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import org.cecil.start.BaseIntegrationSpec
import org.cecil.start.api.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource

import java.nio.charset.Charset

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@TestPropertySource(locations = "/application-test.yml")
class AuthenticationControllerIntegrationSpec extends BaseIntegrationSpec {
    @Autowired
    ObjectMapper objectMapper

    def "GET /user unauthenticated user gets 401"() {
        expect:
        mockMvc.perform(get('/user')).andExpect(status().isUnauthorized())
    }

    def "GET /user authenticated user gets user name response"() {
        expect:
        def principal = buildPrincipal()
        def httpResponse = mockMvc.perform(get('/api/user').session(getMockSession(principal)))
                .andExpect(status().isOk())
                .andReturn()
        def userJson = httpResponse.getResponse().getContentAsString(Charset.defaultCharset())
        def user = objectMapper.readValue(userJson, User)
        user.username == principal.principal.getAttribute("name")
    }


}