package com.gafarov.bastion.controller;

public record RegisterRequest(
        String firstname,
        String lastname,
        String email,
        String password
) {
}
