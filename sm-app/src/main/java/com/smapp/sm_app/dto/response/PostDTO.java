package com.smapp.sm_app.dto.response;

import com.smapp.sm_app.entity.Post;
import lombok.Getter;

import java.util.List;


@Getter
public class PostDTO {

    private Long id;
    private UserDTO user;
    private String title;
    private String text;
    private List<CommentDTO> comments;
    private int likesCount;


    public PostDTO(Post post) {
        this.id = post.getId();
        this.user = new UserDTO(post.getUser());
        if (post.getComments() != null)
            this.comments = post.getComments().stream().map(CommentDTO::new).toList();
        this.title = post.getTitle();
        this.text = post.getText();
        this.likesCount = post.getLikesCount();
    }

}
