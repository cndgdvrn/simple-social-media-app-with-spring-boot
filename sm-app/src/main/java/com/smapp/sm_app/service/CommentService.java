package com.smapp.sm_app.service;

import com.smapp.sm_app.dto.request.comment.CommentCreateRequest;
import com.smapp.sm_app.dto.request.comment.CommentUpdateRequest;
import com.smapp.sm_app.entity.Comment;
import com.smapp.sm_app.entity.Post;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.exception.CommentNotFoundException;
import com.smapp.sm_app.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public Page<Comment> getComments(Optional<Long> postId, Optional<Long> userId, Pageable pageable) {
        if(postId.isPresent() && userId.isPresent()){
            return commentRepository.findByPostIdAndUserId(postId.get(), userId.get(), pageable);
        } else if(postId.isPresent()){
            return commentRepository.findByPostId(postId.get(), pageable);
        } else if(userId.isPresent()){
            return commentRepository.findByUserId(userId.get(), pageable);
        }
        return commentRepository.findAll(pageable);
    }

    public Comment createComment(CommentCreateRequest commentCreateRequest) {
        User user = userService.getUserById(commentCreateRequest.getUserId());
        Post post = postService.getPostById(commentCreateRequest.getPostId());

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setText(commentCreateRequest.getText());
        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long commentId) {
       return commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + commentId));
    }

    public Comment updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = getCommentById(commentId);
        comment.setText(commentUpdateRequest.text());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }
}
