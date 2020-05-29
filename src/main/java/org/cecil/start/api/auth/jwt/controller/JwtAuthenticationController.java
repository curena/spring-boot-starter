package org.cecil.start.api.auth.jwt.controller;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.cecil.start.api.auth.jwt.model.JwtTokenRequest;
import org.cecil.start.api.auth.jwt.model.JwtTokenResponse;
import org.cecil.start.exception.auth.AuthenticationException;
import org.cecil.start.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:3000")//react
@Slf4j
public class JwtAuthenticationController {
    @Value("${jwt.http-request-header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @RequestMapping(value = "${jwt.get-token-uri}", method = RequestMethod.POST)
    public ResponseEntity<JwtTokenResponse> createAuthenticationToken(@RequestBody JwtTokenRequest tokenRequest) {
        authenticate(tokenRequest.getUsername(), tokenRequest.getPassword());
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(tokenRequest.getUsername());
        return ResponseEntity.ok(new JwtTokenResponse(jwtTokenUtil.generateToken(userDetails)));
    }

    @RequestMapping(value = "${jwt.refresh-token-uri}", method = RequestMethod.GET)
    public ResponseEntity<JwtTokenResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        final String authToken = request.getHeader(tokenHeader);
        final String token = authToken == null ? null : authToken.substring(7);

        if (token != null && jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }

}
