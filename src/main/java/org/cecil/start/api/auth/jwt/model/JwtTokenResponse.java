package org.cecil.start.api.auth.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JwtTokenResponse implements Serializable {
    private static final long serialVersionUID = 7232821020131132402L;
    private String token;
}
