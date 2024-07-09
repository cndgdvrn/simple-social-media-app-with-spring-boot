package com.smapp.sm_app.dto.request.like;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

@Data
@ToString
public class LikeCreateRequest {
    @Range(min = 1,message = "Post id must be greater than 0")
    private Long postId;
    @Range(min = 1,message = "User id must be greater than 0")
    private Long userId;
}
