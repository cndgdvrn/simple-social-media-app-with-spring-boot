package com.smapp.sm_app.error;

import com.smapp.sm_app.exception.*;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {

        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        Map<String, String> validationErrors = new HashMap<>();

        if (ex instanceof UserNotFoundException ||
                ex instanceof PostNotFoundException ||
                ex instanceof CommentNotFoundException ||
                ex instanceof LikeNotFoundException
        ) {
            apiError.setMessage(ex.getMessage());
            apiError.setStatus(404);
        }
        else if (ex instanceof MethodArgumentNotValidException) {
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            apiError.setMessage("Validation error");
            apiError.setValidationErrors(validationErrors);
            apiError.setStatus(400);
        }
        else if (ex instanceof DataIntegrityViolationException) {
            String message = ((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage();
            if (message.contains("email")) {
                validationErrors.put("email", "This email is already in use.");
            }
            if (message.contains("username")) {
                validationErrors.put("username", "This username is already in use.");
            }

            apiError.setMessage("Validation error");
            apiError.setValidationErrors(validationErrors);
            apiError.setStatus(400);
        }
        else if(ex instanceof JwtException){
            apiError.setMessage(ex.getMessage());
            apiError.setStatus(401);
        }
        else if(ex instanceof InvalidCredException){
            apiError.setMessage(ex.getMessage());
            apiError.setStatus(401);
        }


        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }


}
