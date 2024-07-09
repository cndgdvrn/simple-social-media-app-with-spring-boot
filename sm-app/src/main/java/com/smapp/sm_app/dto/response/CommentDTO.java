package com.smapp.sm_app.dto.response;

import com.smapp.sm_app.entity.Comment;
import lombok.Getter;

@Getter
public class CommentDTO {
    private Long id;
    private String text;
    private Long userId;
    private Long postId;

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.text = comment.getText();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
    }
}
