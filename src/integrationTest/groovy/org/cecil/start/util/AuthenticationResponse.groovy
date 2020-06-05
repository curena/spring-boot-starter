package org.cecil.start.util

import com.fasterxml.jackson.annotation.JsonProperty

class AuthenticationResponse {
    @JsonProperty("access_token")
    def accessToken
    @JsonProperty("token_type")
    def tokenType
}