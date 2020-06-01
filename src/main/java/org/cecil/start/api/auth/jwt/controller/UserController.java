package org.cecil.start.api.auth.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.cecil.start.api.auth.jwt.model.UserModel;
import org.cecil.start.service.auth.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(userService.signUpUser(userModel));
    }
}
