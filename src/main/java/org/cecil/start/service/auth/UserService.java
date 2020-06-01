package org.cecil.start.service.auth;

import lombok.RequiredArgsConstructor;
import org.cecil.start.api.auth.jwt.model.UserModel;
import org.cecil.start.db.entity.UserEntity;
import org.cecil.start.db.repository.UserRepository;
import org.cecil.start.service.mapping.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public String signUpUser(UserModel userModel) {
        UserEntity userEntity = userMapper.toEntity(userModel);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        userRepository.save(userEntity);
        return "User " + userEntity.getUsername() + " signed up successfully.";
    }
}
