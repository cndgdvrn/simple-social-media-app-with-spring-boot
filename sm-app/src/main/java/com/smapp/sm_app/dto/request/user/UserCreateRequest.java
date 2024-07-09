package com.smapp.sm_app.dto.request.user;


import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.validator.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserCreateRequest {

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
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        return user;
    }
}
