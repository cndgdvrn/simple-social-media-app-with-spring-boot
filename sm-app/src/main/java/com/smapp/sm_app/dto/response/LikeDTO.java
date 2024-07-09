package com.smapp.sm_app.dto.response;

import com.smapp.sm_app.entity.Like;
import lombok.Getter;

@Getter
public class LikeDTO {
    private Long id;
    private Long postId;
    private Long userId;

    public LikeDTO(Like like) {
        this.id = like.getId();
        this.postId = like.getPost().getId();
        this.userId = like.getUser().getId();
    }
}
