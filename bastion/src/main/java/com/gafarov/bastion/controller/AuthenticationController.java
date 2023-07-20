package com.gafarov.bastion.controller;

import com.gafarov.bastion.config.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public AuthenticationResponse register(
            @RequestBody RegisterRequest request
    ){
        return authenticationService.register(request);
    }
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return authenticationService.authenticate(request);
    }

}
