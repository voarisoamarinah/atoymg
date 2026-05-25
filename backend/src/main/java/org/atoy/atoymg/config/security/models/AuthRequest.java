package org.atoy.atoymg.config.security.models;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") String password
) {
    public String username() {
        return username;
    }

    public String password() {
        return password;
    }
}
