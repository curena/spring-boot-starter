package org.cecil.start.service.auth;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.cecil.start.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService jwtUserDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    private String tokenHeader;

    @Autowired
    public JwtTokenAuthorizationOncePerRequestFilter(UserDetailsService jwtUserDetailsService,
                                                     JwtTokenUtil jwtTokenUtil,
                                                     @Value("${jwt.http.request.header}") String tokenHeader) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.debug("Authentication Request for '{}", request.getRequestURI());
        setAuthentication(request);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(final HttpServletRequest request) {
        final String jwtToken = getJwtToken(request.getHeader(this.tokenHeader));
        UserDetails userDetails = getUserDetails(jwtToken);
        if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
            setAuthenticationToken(request, userDetails);
        }
    }

    private String getJwtToken(String requestTokenHeader) {
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
        } else {
            log.warn("JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING");
        }
        return jwtToken;
    }

    private UserDetails getUserDetails(String jwtToken) {
        final String username = getUsername(jwtToken);
        log.debug("JWT_TOKEN_USERNAME_VALUE '{}'", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            return jwtUserDetailsService.loadUserByUsername(username);
        }
        return null;
    }

    private String getUsername(String jwtToken) {
        String username = null;
        try {
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        } catch (IllegalArgumentException ex) {
            log.error("JWT_TOKEN_UNABLE_TO_GET_USERNAME", ex);
        } catch (ExpiredJwtException ex) {
            log.warn("JWT_TOKEN_EXPIRED", ex);
        }
        return username;
    }

    private void setAuthenticationToken(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

}
