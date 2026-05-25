package org.atoy.atoymg.config.security.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
