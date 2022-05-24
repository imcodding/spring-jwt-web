package com.jwt.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @PostMapping("/api/v1/login")
    public String login(@RequestParam String email, @RequestParam String password) {

        log.info("email={}", email);
        log.info("password={}", password);

        return "ok";
    }
}
