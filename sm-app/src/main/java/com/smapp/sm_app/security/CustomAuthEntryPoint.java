package com.smapp.sm_app.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smapp.sm_app.error.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;

@Configuration
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiError apiError = new ApiError();
        apiError.setMessage(authException.getMessage());
        apiError.setPath(request.getRequestURI());
        apiError.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");
        response.setStatus(apiError.getStatus());
        OutputStream os = response.getOutputStream();

        objectMapper.writeValue(os, apiError);
        os.flush();
    }
}
