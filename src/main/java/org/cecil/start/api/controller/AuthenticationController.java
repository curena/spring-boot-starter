package org.cecil.start.api.controller;

import org.cecil.start.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.cecil.start.util.Constants.BASE_URL;

@CrossOrigin(origins = { "http://localhost:3000" })
@RestController
public class AuthenticationController {
//	@GetMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Map<String, Object>> user(
//			@AuthenticationPrincipal OAuth2User authPrincipal) {
//		Map<String, Object> userResponse = new HashMap<>();
//		userResponse.put("name", authPrincipal.getAttribute("name"));
//		return ResponseEntity.ok(userResponse);
//	}

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping(BASE_URL + "/basicauth")
    public ResponseEntity<String> basicAuth() {
        return ResponseEntity.accepted().body(authenticationService.getAuthenticationMessage());
    }

}
