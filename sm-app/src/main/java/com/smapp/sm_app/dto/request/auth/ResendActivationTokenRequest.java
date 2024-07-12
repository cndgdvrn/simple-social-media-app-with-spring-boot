package com.smapp.sm_app.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResendActivationTokenRequest {
    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;
}
