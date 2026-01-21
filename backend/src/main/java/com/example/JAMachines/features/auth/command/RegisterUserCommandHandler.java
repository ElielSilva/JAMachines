package com.example.JAMachines.features.auth.command;

import com.example.JAMachines.domain.entity.User;
import com.example.JAMachines.features.auth.RegisterUserCommand;
import com.example.JAMachines.infrestructure.persistence.UserRepository;
import com.example.JAMachines.infrestructure.security.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserCommandHandler {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public RegisterUserCommandHandler(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public String handle(RegisterUserCommand command) {
        if (userRepository.findByEmail(command.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = User.builder()
                .name(command.name())
                .email(command.email())
                .password(passwordEncoder.encode(command.password()))
                .build();

        User userCreated = userRepository.save(user);

        return tokenService.generateToken(userCreated);
    }
}
