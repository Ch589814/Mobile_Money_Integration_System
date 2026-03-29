package com.example.mobile_money_integration_system.security;

import com.example.mobile_money_integration_system.entity.User;
import com.example.mobile_money_integration_system.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public OAuth2SuccessHandler(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String fullName = oAuth2User.getAttribute("name");

        if (email == null) {
            throw new RuntimeException("Email not found from Google account");
        }

        String username = email.split("@")[0];

        if (userRepository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setFullName(fullName);
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword("OAUTH2_USER");
            user.setBalance(0.0);
            user.setRoles(Set.of("ROLE_USER"));
            userRepository.save(user);
        }

        String token = jwtUtil.generateToken(username);
        response.sendRedirect("http://localhost:8080/oauth-success?token=" + token);
    }
}
