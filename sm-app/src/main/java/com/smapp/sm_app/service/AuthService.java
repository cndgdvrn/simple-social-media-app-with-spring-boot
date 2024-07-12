

package com.smapp.sm_app.service;

import com.smapp.sm_app.dto.request.auth.LoginRequest;
import com.smapp.sm_app.dto.request.auth.RegisterRequest;
import com.smapp.sm_app.dto.response.AuthDTO;
import com.smapp.sm_app.dto.response.UserDTO;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.exception.InvalidCredException;
import com.smapp.sm_app.pojo.MailMessageContent;
import com.smapp.sm_app.repository.UserRepository;
import com.smapp.sm_app.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    public AuthDTO login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new InvalidCredException("Invalid username or password"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()) || !user.isActive()) {
            throw new InvalidCredException(!user.isActive() ? "User is not activated" : "Invalid username or password");
        }
        String token = tokenProvider.createJwtToken(user);
        return new AuthDTO(token, new UserDTO(user));
    }

    @Transactional
    public User register(RegisterRequest registerRequest) {
        User user = registerRequest.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        rabbitTemplate.convertAndSend("topic_exchange",
                "activation", new MailMessageContent(user.getUsername(),user.getEmail(), user.getActivationToken()));
        return userRepository.save(user);
//        User user = registerRequest.toUser();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//        String token = tokenProvider.createJwtToken(savedUser);
//        return new AuthDTO(token,new UserDTO(savedUser));
    }

    public String activateUser(String token) {
        User user = userService.findByActivationToken(token);
        user.setActive(true);
        user.setActivationToken(null);
        userRepository.save(user);
        return "User activated successfully";
    }
}
