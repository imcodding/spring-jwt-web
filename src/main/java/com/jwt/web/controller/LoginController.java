package com.jwt.web.controller;

import com.jwt.provider.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.jwt.provider.JwtAuthProvider.CLIENT_TOKEN;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtAuthProvider jwtAuthProvider;

    @PostMapping("/api/v1/login")
    public String login(@RequestParam String email, @RequestParam String password) {

        log.info("email={}", email);
        log.info("password={}", password);

        Optional<String> jwtAuthToken = jwtAuthProvider.createJwtAuthToken(email);
        jwtAuthToken.ifPresent(s -> CLIENT_TOKEN = s);

        log.info("client get token: {}", CLIENT_TOKEN);

        return CLIENT_TOKEN;
    }
}
