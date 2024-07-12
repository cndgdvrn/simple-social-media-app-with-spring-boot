package com.smapp.sm_app.dto.request.auth;

import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {

    @Size(min = 3, max = 30)
    @Unique(fieldName = "username")
    private String username;

    @Email
    @NotBlank
    @Unique(fieldName = "email")
    private String email;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must contains minimum eight characters, at least one uppercase letter, one lowercase letter and one number ")
    private String password;

    @Size(min = 3, max = 30)
    private String name;

    @Size(min = 3, max = 30)
    private String surname;

    public User toUser(){
        User user = new User();
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setSurname(this.surname);
        return user;
    }

}
