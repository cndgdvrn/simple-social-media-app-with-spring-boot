package com.smapp.sm_app.service;

import com.smapp.sm_app.dto.request.auth.LoginRequest;
import com.smapp.sm_app.dto.response.AuthDTO;
import com.smapp.sm_app.dto.response.UserDTO;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.exception.InvalidCredException;
import com.smapp.sm_app.repository.UserRepository;
import com.smapp.sm_app.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthDTO login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new InvalidCredException("Invalid username or password")
        );
        if (passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())) {
            String token = tokenProvider.createJwtToken(user);
            return new AuthDTO(token,new UserDTO(user));
        }
        throw new InvalidCredException("Invalid username or password");
    }
}
