package com.smapp.sm_app.dto.request.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
       @NotBlank(message = "Comment can not be blank") String text) {
}
