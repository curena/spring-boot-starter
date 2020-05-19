package org.cecil.auth

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class BaseIntegrationSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    protected faker = new Faker()

    def "unauthorized user should get redirected to auth provider"() {
        expect:
        mockMvc
        mockMvc.perform(get('/actuator/health')).andExpect(status().is3xxRedirection())
    }
}