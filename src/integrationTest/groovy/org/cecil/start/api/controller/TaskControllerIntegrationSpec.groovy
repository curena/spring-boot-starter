package org.cecil.start.api.controller

import org.cecil.start.BaseWebMvcIntegrationSpec

import java.nio.charset.Charset

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TaskControllerIntegrationSpec extends BaseWebMvcIntegrationSpec {
    def "Tasks happy path"() {
//        expect:
//        def httpResponse = mockMvc.perform(get('/tasks')
//                .header("Authorization", "Bearer ${generateValidToken()}"))
//                .andExpect(status().isOk())
//                .andReturn()
//        def response = httpResponse.getResponse().getContentAsString(Charset.defaultCharset())
//        response == "Hello, World!"

        //POST task
        //GET tasks
        //PUT task
        //DELETE task
    }
}
