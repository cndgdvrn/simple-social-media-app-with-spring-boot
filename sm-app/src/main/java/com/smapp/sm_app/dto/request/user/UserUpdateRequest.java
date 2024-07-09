package com.smapp.sm_app.dto.request.user;


import com.smapp.sm_app.validator.Unique;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @Unique(fieldName = "username")
    private String username;

    @Unique(fieldName = "email")
    private String email;

    private String password;
}
