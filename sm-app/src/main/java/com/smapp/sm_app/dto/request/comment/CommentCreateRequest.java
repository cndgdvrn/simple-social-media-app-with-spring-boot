package com.smapp.sm_app.dto.request.comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class CommentCreateRequest {

    @Range(min = 1, message = "Post id must be greater than 0")
    private Long postId;

    @Range(min = 1, message = "User id must be greater than 0")
    private Long userId;

    @NotBlank(message = "Comment text cannot be empty")
    private String text;

}
