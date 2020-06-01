package org.cecil.start.service.auth;

import lombok.RequiredArgsConstructor;
import org.cecil.start.db.entity.UserEntity;
import org.cecil.start.db.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.cecil.start.util.Constants.USER_ROLE;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new JwtUserDetails(userEntity.getUsername(), userEntity.getPassword(), USER_ROLE);
    }
}
