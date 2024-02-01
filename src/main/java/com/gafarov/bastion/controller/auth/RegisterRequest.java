package com.gafarov.bastion.controller.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegisterRequest(
        @NotEmpty
        String firstname,
        @NotEmpty
        String lastname,
        @NotEmpty
        @Email
        String email,
        @NotEmpty
        String password
) {
}
