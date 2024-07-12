package com.smapp.sm_app.controller;

import com.smapp.sm_app.dto.request.user.UserCreateRequest;
import com.smapp.sm_app.dto.request.user.UserUpdateRequest;
import com.smapp.sm_app.dto.response.UserDTO;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.response_template.ResponseTemplate;
import com.smapp.sm_app.security.CurrentUser;
import com.smapp.sm_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        List<User> userList = userService.getUsers();
        List<UserDTO> collected = userList.stream().map(UserDTO::new).toList();
        ResponseTemplate<List<UserDTO>> responseTemplate = new ResponseTemplate<>("Users fetched successfully", collected);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id, @AuthenticationPrincipal CurrentUser currentUser) {
        User user = userService.getUserById(id);
        UserDTO userDTO = new UserDTO(user);
        ResponseTemplate<UserDTO> responseTemplate = new ResponseTemplate<>("User fetched successfully", userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        User user = userService.createUser(userCreateRequest);
        UserDTO userDTO = new UserDTO(user);
        ResponseTemplate<UserDTO> responseTemplate = new ResponseTemplate<>("User created successfully", userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseTemplate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseTemplate<?> responseTemplate = new ResponseTemplate<>("User deleted successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        User user = userService.updateUser(id, userUpdateRequest);
        UserDTO userDTO = new UserDTO(user);
        ResponseTemplate<UserDTO> responseTemplate = new ResponseTemplate<>("User updated successfully", userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }


}
