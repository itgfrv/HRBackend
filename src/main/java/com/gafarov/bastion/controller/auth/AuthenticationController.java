package com.gafarov.bastion.controller.auth;

import com.gafarov.bastion.controller.BaseController;
import com.gafarov.bastion.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public AuthenticationResponse register(
            @RequestBody RegisterRequest request
    ) {
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return authenticationService.authenticate(request);
    }

}
