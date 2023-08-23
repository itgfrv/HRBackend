package com.gafarov.bastion.controller.auth;

public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
