package org.cecil.start


import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import org.cecil.start.util.AuthenticationRequest
import org.cecil.start.util.AuthenticationResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.http.ReactiveHttpOutputMessage
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import spock.lang.Specification

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity

@AutoConfigureDataJpa
@ActiveProfiles(["test", "local"])
@WebMvcTest
class BaseWebMvcIntegrationSpec extends Specification {
    @Autowired
    WebApplicationContext webAppContext

    @Autowired
    protected ObjectMapper objectMapper

    protected MockMvc mockMvc

    @Value('${auth0.client-id}')
    private String clientId

    @Value('${auth0.client-secret}')
    private String clientSecret

    @Value('${auth0.api-audience}')
    private String audience

    @Value('${auth0.grant-type}')
    private String grantType

    @Value('${auth0.issuer}')
    private String authBaseUrl

    @Value('${auth0.authorization-uri}')
    private String authorizationUri

    protected static faker = new Faker()

    def setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webAppContext)
                .apply(springSecurity()).build()
    }

    def authenticationRequest() {
        AuthenticationRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .audience(audience)
                .grantType(grantType).build()
    }

    def getAuth0Token() {
        def webClient = WebClient.create(authBaseUrl)
        BodyInserter<AuthenticationRequest, ReactiveHttpOutputMessage> bodyInserter =
                BodyInserters.fromValue(authenticationRequest())
        def postResponse = webClient.post().uri(authorizationUri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(bodyInserter)
                .retrieve()
                .bodyToMono(String)
                .block()
        objectMapper.readValue(postResponse, AuthenticationResponse)
    }
}