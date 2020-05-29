package org.cecil.start


import com.github.javafaker.Faker
import org.springframework.context.annotation.Profile
import spock.lang.Specification

@Profile("test")
class BaseJpaIntegrationSpec extends Specification {

    protected static faker = new Faker()

}