package org.cecil.start.service.auth


import org.cecil.start.BaseWebMvcIntegrationSpec
import org.cecil.start.api.auth.jwt.model.UserModel
import org.cecil.start.db.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceIntegrationSpec extends BaseWebMvcIntegrationSpec {

    @Autowired
    UserService userService

    @Autowired
    PasswordEncoder bCryptPasswordEncoder

    @Autowired
    UserRepository userRepository

    def "should sign up a user"() {
        given:
        def username = faker.name().username()
        def password = faker.internet().password()
        def userModel = UserModel.builder().username(username).password(password).build()

        when:
        userService.signUpUser(userModel)

        then:
        def userEntity = userRepository.findByUsername(username)
        userEntity
        bCryptPasswordEncoder.matches(password, userEntity.password)
    }
}
