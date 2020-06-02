package org.cecil.start.api.auth.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class JwtTokenResponse implements Serializable {
    private static final long serialVersionUID = 7232821020131132402L;
    private String token;
    private String error;
}