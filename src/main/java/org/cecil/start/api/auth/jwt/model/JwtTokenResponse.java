package org.cecil.start.api.auth.jwt.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class JwtTokenResponse implements Serializable {
    private static final long serialVersionUID = 7232821020131132402L;
    private final String token;
}
