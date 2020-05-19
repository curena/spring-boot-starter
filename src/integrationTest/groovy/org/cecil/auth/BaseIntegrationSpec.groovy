package org.cecil.auth

import com.github.javafaker.Faker
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@WebMvcTest
class BaseIntegrationSpec extends Specification {
    @Autowired
    protected MockMvc mockMvc

    protected faker = new Faker()
}