package org.cecil.start.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public String getAuthenticationMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello, " + authentication.getName() + ". You are authenticated.";
    }
}
