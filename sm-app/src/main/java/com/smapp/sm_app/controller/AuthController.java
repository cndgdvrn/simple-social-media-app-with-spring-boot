package com.smapp.sm_app.controller;

import com.smapp.sm_app.dto.request.auth.LoginRequest;
import com.smapp.sm_app.dto.response.AuthDTO;
import com.smapp.sm_app.response_template.ResponseTemplate;
import com.smapp.sm_app.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthDTO authDTO = authService.login(loginRequest);
        ResponseTemplate<AuthDTO> responseTemplate = new ResponseTemplate("Login successful", authDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }
}
