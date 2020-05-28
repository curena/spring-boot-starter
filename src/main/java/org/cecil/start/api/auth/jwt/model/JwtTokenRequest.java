package org.cecil.start.api.auth.jwt.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenRequest implements Serializable {
    private static final long serialVersionUID = 5517724239050644808L;

    private String username;
    private String password;
}