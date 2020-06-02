package org.cecil.start

import com.github.javafaker.Faker
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@ActiveProfiles("test")
@DataJpaTest
class BaseJpaIntegrationSpec extends Specification {
    protected static faker = new Faker()
}
