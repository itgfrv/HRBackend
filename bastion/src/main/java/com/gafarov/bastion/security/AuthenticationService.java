package com.gafarov.bastion.security;

import com.gafarov.bastion.controller.auth.AuthenticationRequest;
import com.gafarov.bastion.controller.auth.AuthenticationResponse;
import com.gafarov.bastion.controller.auth.RegisterRequest;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.entity.user.UserStatus;
import com.gafarov.bastion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final TokenService tokenService;
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
                .activity(Activity.REGISTERED)
                .userStatus(UserStatus.REJECT)
                .build();
        var savedUser = userService.addNewUser(user);
        var jwtToken = jwtService.generateToken(user);
        tokenService.saveUserToken(savedUser, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        User user = userService.findUserByEmail(request.email());
        var jwtToken = jwtService.generateToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken);
        return new AuthenticationResponse(jwtToken);
    }
    public void createAdmin(){
        User user = User.builder()
                .firstname("admin")
                .lastname("admin")
                .email("admin@mail.ru")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .activity(Activity.REGISTERED)
                .userStatus(UserStatus.REJECT)
                .build();
        var savedUser = userService.addNewUser(user);

    }
}
