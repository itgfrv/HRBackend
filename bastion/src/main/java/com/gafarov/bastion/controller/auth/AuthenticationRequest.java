package com.gafarov.bastion.controller.auth;

public record AuthenticationRequest(
        String email,
        String password
) {
}
