package org.cecil.start.api.controller;

import org.cecil.start.api.model.HelloWorld;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = { "http://localhost:3000" }) //3000 is react
@RestController
public class HelloWorldController {

    @GetMapping("/api/hello")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok(new HelloWorld().getHello());
    }
}
