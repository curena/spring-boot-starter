package org.cecil.start.api.controller

import org.cecil.start.BaseIntegrationSpec
import spock.lang.Ignore

import java.nio.charset.Charset

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HelloWorldControllerIntegrationSpec extends BaseIntegrationSpec {

    @Ignore
    def "should get 200 and hello world response"() {
//        def principal = buildPrincipal()
        //.session(getMockSession(principal)
        expect:
        def httpResponse = mockMvc.perform(get('/api/hello'))
                .andExpect(status().isOk())
                .andReturn()
        def response = httpResponse.getResponse().getContentAsString(Charset.defaultCharset())
        response == "Hello, World!"
    }
}
