package com.smapp.sm_app.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError{

    private String message;
    private int status;
    private Map<String,String> validationErrors;
    private String path;
    private Long timestamp = new Date().getTime();

    public ApiError(String message, int status, String path) {
        this.message = message;
        this.status = status;
        this.path = path;
    }

}
