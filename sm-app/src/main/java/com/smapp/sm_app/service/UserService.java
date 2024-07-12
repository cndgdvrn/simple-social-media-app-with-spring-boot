package com.smapp.sm_app.service;

import com.smapp.sm_app.dto.request.user.UserCreateRequest;
import com.smapp.sm_app.dto.request.user.UserUpdateRequest;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.exception.InvalidCredException;
import com.smapp.sm_app.exception.UserNotFoundException;
import com.smapp.sm_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }

    public User createUser(UserCreateRequest userCreateRequest) {
        try {
            User user = userCreateRequest.toUser();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Error creating user", ex);
        }
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User userInDB = getUserById(id);
        userInDB.setUsername(userUpdateRequest.getUsername());
        userInDB.setEmail(userUpdateRequest.getEmail());
        userInDB.setPassword(userUpdateRequest.getPassword());
        return userRepository.save(userInDB);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return user;
    }

    public User findByActivationToken(String token) {
        User user = userRepository.findByActivationToken(token).orElseThrow(() -> new InvalidCredException("Token is not valid " + token));
        return user;
    }

}
