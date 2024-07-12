package com.smapp.sm_app.controller;

import com.smapp.sm_app.dto.request.auth.ResendActivationTokenRequest;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.service.MailService;
import com.smapp.sm_app.dto.request.auth.RegisterRequest;
import com.smapp.sm_app.dto.request.auth.LoginRequest;
import com.smapp.sm_app.dto.response.AuthDTO;
import com.smapp.sm_app.response_template.ResponseTemplate;
import com.smapp.sm_app.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {

        AuthDTO authDTO = authService.login(loginRequest);
        ResponseTemplate<AuthDTO> responseTemplate = new ResponseTemplate<>("Login successful", authDTO);
        ResponseCookie cookie = ResponseCookie.from("auth-token", authDTO.getToken())
                .httpOnly(true)
                .maxAge(60 * 60 * 12) // 12 hours
                .path("/")
                .sameSite("Strict")
                .secure(true)
                .build();
        return ResponseEntity.status(HttpStatus.OK).header("Set-Cookie", cookie.toString()).body(responseTemplate);


//        AuthDTO authDTO = authService.login(loginRequest);
//        ResponseTemplate<AuthDTO> responseTemplate = new ResponseTemplate("Login successful", authDTO);
//        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {

        User user = authService.register(registerRequest);
        ResponseTemplate<?> responseTemplate = new ResponseTemplate<>("Check your mailbox to activate account"
                , null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseTemplate);
    }



//    @PostMapping("/resend-activation")
//    public ResponseEntity<?> resendActivation(@RequestBody ResendActivationTokenRequest request) {
//        mailService.sendActivationEmail(request.getEmail());
//        ResponseTemplate<?> responseTemplate = new ResponseTemplate<>("Activation email sent", null);
//        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
//    }

    @GetMapping("/activate")
    public ResponseEntity<ResponseTemplate<?>> activateUser(@RequestParam String token) {
        String response = authService.activateUser(token);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseTemplate<>(response, null));
    }
}

