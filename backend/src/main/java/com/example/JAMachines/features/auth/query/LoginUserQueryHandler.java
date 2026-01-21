package com.example.JAMachines.features.auth.query;

import com.example.JAMachines.application.common.exceptions.AuthException;
import com.example.JAMachines.domain.entity.User;
import com.example.JAMachines.features.auth.LoginUserQuery;
import com.example.JAMachines.infrestructure.persistence.UserRepository;
import com.example.JAMachines.infrestructure.security.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginUserQueryHandler {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginUserQueryHandler(UserRepository userRepository, TokenService tokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public String handle(LoginUserQuery query) {
        User user = userRepository.findByEmail(query.email())
                .orElseThrow(() -> new AuthException("Invalid email or password"));

        if (!passwordEncoder.matches(query.password(), user.getPassword())) {
            throw new AuthException("Invalid email or password");
        }

        return tokenService.generateToken(user);
    }
}