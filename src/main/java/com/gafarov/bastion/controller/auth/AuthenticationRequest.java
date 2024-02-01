package com.gafarov.bastion.controller.auth;

import jakarta.validation.constraints.NotEmpty;

public record AuthenticationRequest(
        @NotEmpty
        String email,
        @NotEmpty
        String password
) {
}
