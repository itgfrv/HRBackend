package com.gafarov.bastion.config;

import com.gafarov.bastion.controller.AuthenticationRequest;
import com.gafarov.bastion.controller.AuthenticationResponse;
import com.gafarov.bastion.controller.RegisterRequest;
import com.gafarov.bastion.exception.ConflictDataException;
import com.gafarov.bastion.entity.Role;
import com.gafarov.bastion.entity.User;
import com.gafarov.bastion.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();
        try {
            repository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new ConflictDataException(
                    String.format("Email %s is already in use", request.email()),
                    exception
            );
        }
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = repository.findByEmail(request.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
