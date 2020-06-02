package org.cecil.start.api.auth.jwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.cecil.start.api.auth.jwt.model.JwtTokenResponse;
import org.cecil.start.exception.auth.AuthenticationException;
import org.cecil.start.util.Constants;
import org.cecil.start.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.cecil.start.util.Constants.REFRESH_TOKEN_URL;
import static org.cecil.start.util.Constants.TOKEN_PREFIX;

@RestController
@Slf4j
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = REFRESH_TOKEN_URL, method = RequestMethod.GET)
    public ResponseEntity<JwtTokenResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        final String authToken = request.getHeader(Constants.AUTH_HEADER);
        final String token = authToken == null ? null : authToken.replace(TOKEN_PREFIX, "");

        if (token != null && jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(JwtTokenResponse.builder().token(refreshedToken).build());
        } else {
            return ResponseEntity.badRequest().body(
                    JwtTokenResponse.builder().token(null).error("Token could not be refreshed.").build()
            );
        }
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
