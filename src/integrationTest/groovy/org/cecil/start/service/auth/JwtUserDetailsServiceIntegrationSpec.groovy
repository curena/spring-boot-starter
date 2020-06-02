package org.cecil.start.service.auth


import org.cecil.start.BaseWebMvcIntegrationSpec
import org.cecil.start.db.entity.UserEntity
import org.cecil.start.db.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class JwtUserDetailsServiceIntegrationSpec extends BaseWebMvcIntegrationSpec {
    @Autowired
    UserRepository userRepository

    @Autowired
    UserDetailsService jwtUserDetailsService

    def "loadUserByUsername should return JwtUserDetails when user exists"() {
        given:
        def username = faker.name().username()
        def password = faker.internet().password()
        def userEntity = new UserEntity(username, password)
        userRepository.save(userEntity)

        when:
        def userDetails = jwtUserDetailsService.loadUserByUsername(username)

        then:
        userDetails.getUsername() == username
        userDetails.getPassword() == password
        userDetails.getAuthorities().first().authority == "USER"
    }

    def "loadUserByUsername should throw UserNameNotFoundException when user does not exist"() {
        when:
        jwtUserDetailsService.loadUserByUsername(faker.name().username())

        then:
        thrown(UsernameNotFoundException)
    }
}
