package org.cecil.start.client;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

//@Configuration
//@EnableAutoConfiguration
public class ClientConfig {
//    @Bean
//    WebClient webClient(ClientRegistrationRepository clientRegistrations,
//                        OAuth2AuthorizedClientRepository authorizedClients) {
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oAuth2 =
//                new ServletOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients);
//        oAuth2.setDefaultOAuth2AuthorizedClient(true);
//        oAuth2.setDefaultClientRegistrationId("github");
//
//        return WebClient.builder().apply(oAuth2.oauth2Configuration()).build();
//    }
}
