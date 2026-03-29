package com.example.mobile_money_integration_system.contoller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OAuthController {

    @GetMapping("/oauth-success")
    public Map<String, String> oauthSuccess(@RequestParam String token) {
        return Map.of(
                "message", "Google login successful",
                "token", token
        );
    }
}
