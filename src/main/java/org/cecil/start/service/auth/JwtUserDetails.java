package org.cecil.start.service.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JwtUserDetails extends User {
    private static final long serialVersionUID = 101134442833166839L;

    @Getter(onMethod_=@JsonIgnore)
    private final UUID id = UUID.randomUUID();

    public JwtUserDetails(String username, String password, String role) {
        super(username, password, List.of(new SimpleGrantedAuthority(role)));
    }

    public JwtUserDetails() {
        super("", "", new ArrayList<>());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
