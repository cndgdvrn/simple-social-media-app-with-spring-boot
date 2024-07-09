package com.smapp.sm_app.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostUpdateRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
}
