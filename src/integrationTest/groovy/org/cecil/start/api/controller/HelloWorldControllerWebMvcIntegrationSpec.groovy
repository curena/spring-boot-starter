package org.cecil.start.api.controller

import org.cecil.start.BaseWebMvcIntegrationSpec

import java.nio.charset.Charset

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class HelloWorldControllerWebMvcIntegrationSpec extends BaseWebMvcIntegrationSpec {

    def "should get 200 and hello world response"() {
        expect:
        def httpResponse = mockMvc.perform(get('/api/hello')
                .header("Authorization", "Bearer ${generateValidToken()}"))
                .andExpect(status().isOk())
                .andReturn()
        def response = httpResponse.getResponse().getContentAsString(Charset.defaultCharset())
        response == "Hello, World!"
    }
}
