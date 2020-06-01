package org.cecil.start.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static org.cecil.start.util.Constants.SECRET;
import static org.cecil.start.util.Constants.TOKEN_PREFIX;

@Component
public class JwtTokenUtil {
    @Value("${jwt.token.expiration-in-seconds}")
    private Long expirationTimeInSeconds;

    private final JWTVerifier jwtVerifier;

    public JwtTokenUtil() {
        jwtVerifier = JWT.require(HMAC512(SECRET.getBytes())).build();
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));
        return decodedJWT.getSubject();
    }

    public Date getIssuedAtDateFromToken(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));
        return decodedJWT.getIssuedAt();
    }

    public Date getExpirationDateFromToken(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token.replace(TOKEN_PREFIX, ""));
        return decodedJWT.getExpiresAt();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public String refreshToken(String token) {
        return generateToken(getUsernameFromToken(token));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

    public String generateToken(String username) {
        final long createdDate = System.currentTimeMillis();

        return JWT.create()
                .withIssuedAt(new Date(createdDate))
                .withSubject(username)
                .withExpiresAt(calculateExpirationDate(createdDate))
                .sign(HMAC512(SECRET.getBytes()));
    }

    private Date calculateExpirationDate(long createdDate) {
        return new Date(createdDate + expirationTimeInSeconds * 1000);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return userDetails != null && (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
