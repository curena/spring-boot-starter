package org.cecil.start

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.builder.Builder

@Builder
class AuthenticationRequest {
    @JsonProperty("client_id")
    def clientId
    @JsonProperty("client_secret")
    def clientSecret
    def audience
    @JsonProperty("grant_type")
    def grantType
}
