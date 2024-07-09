package com.smapp.sm_app.dto.response;

import com.smapp.sm_app.entity.User;
import lombok.Getter;

@Getter
public class UserDTO {

    private Long id;

    private String username;
    private String email;
    private String name;
    private String surname;
    private boolean active;
    private boolean isDeleted;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.active = user.isActive();
        this.isDeleted = user.isDeleted();
    }
}
