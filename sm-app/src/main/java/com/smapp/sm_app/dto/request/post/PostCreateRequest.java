package com.smapp.sm_app.dto.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class PostCreateRequest {

    @Range(min = 1,message = "User id must be greater than 0")
    private Long userId;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

}
