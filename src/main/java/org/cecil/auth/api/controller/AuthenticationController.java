package org.cecil.auth.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {
	@GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> user(
			@AuthenticationPrincipal OAuth2User authPrincipal) {
		Map<String, Object> userResponse = new HashMap<>();
		userResponse.put("name", authPrincipal.getAttribute("name"));
		return ResponseEntity.ok(userResponse);
	}
}
