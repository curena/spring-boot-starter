package org.cecil.start.util

import groovy.util.logging.Slf4j
import org.cecil.start.BaseSpec
import org.cecil.start.service.auth.JwtUserDetails

import java.time.Instant

class JwtTokenUtilSpec extends BaseSpec {
    def jwtTokenUtil = new JwtTokenUtil()
    def secret = faker.internet().password()
    def expirationInSeconds = 300

    def setup() {
        jwtTokenUtil.secret = secret
        jwtTokenUtil.expiration = expirationInSeconds
    }

    def "JWT generation happy path"() {
        given:
        def userDetails = JwtUserDetails.withDefaultPasswordEncoder()
                .username(faker.name().firstName())
                .password("bar")
                .roles("USER").build()

        when:
        def token = jwtTokenUtil.generateToken(userDetails)

        then:
        !token.isBlank()
        jwtTokenUtil.validateToken(token, userDetails)
        jwtTokenUtil.getExpirationDateFromToken(token)
                .after(Date.from(Instant.ofEpochSecond(expirationInSeconds)))
        jwtTokenUtil.canTokenBeRefreshed(token)
    }


}
