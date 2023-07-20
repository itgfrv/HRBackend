package com.gafarov.bastion.controller;

public record AuthenticationRequest(
        String email,
        String password
) {
}
