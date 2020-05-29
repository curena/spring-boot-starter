package org.cecil.start.service.auth;

import org.cecil.start.api.model.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO move from inMemoryList to DB eventually.
@Service
public class JwtUserDetailsService implements UserDetailsService {
    public String getAuthenticationMessage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "Hello, " + authentication.getName() + ". You are authenticated.";
    }

    static List<JwtUserDetails> inMemoryUserList = new ArrayList<>();

    static {
        inMemoryUserList.add(new JwtUserDetails("foo",
                "$2y$12$nS8XxKuV4ed4V1mXfZPth.TyKktse1k7pfDnEuA98EhgvdYwnk90y", "ROLE_USER_2")); //`bar` in bcrypt
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<JwtUserDetails> findFirst = inMemoryUserList.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst();

        if (findFirst.isEmpty()) {
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
        }

        return findFirst.get();
    }
}
