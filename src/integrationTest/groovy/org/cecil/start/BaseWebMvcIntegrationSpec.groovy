package org.cecil.start

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import org.cecil.start.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.mock.web.MockHttpSession
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY

@ActiveProfiles("test")
@WebMvcTest
class BaseWebMvcIntegrationSpec extends Specification {
    @Autowired
    WebApplicationContext webAppContext

    @Autowired
    protected ObjectMapper objectMapper

    protected MockMvc mockMvc

    @Autowired
    protected JwtTokenUtil jwtTokenUtil

    protected static faker = new Faker()

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .apply(springSecurity()).build()
    }

    static getMockSession(OAuth2AuthenticationToken principal) {
        def session = new MockHttpSession()
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, new SecurityContextImpl(principal))
        session
    }

    static OAuth2AuthenticationToken buildPrincipal() {

        Map<String, Object> attributes = new HashMap<>()
        attributes.put("sub", "fakeId")
        attributes.put("name", faker.artist().name())
        attributes.put("email", faker.internet().emailAddress())

        List<GrantedAuthority> authorities = Collections.singletonList(
                new OAuth2UserAuthority("ROLE_USER", attributes))
        def user = new DefaultOAuth2User(authorities, attributes, "sub")
        new OAuth2AuthenticationToken(user, authorities, "whatever")
    }

    def generateValidToken() {
        jwtTokenUtil.generateToken("foo")
    }
}