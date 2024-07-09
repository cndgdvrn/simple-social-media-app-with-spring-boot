package com.smapp.sm_app.controller;

import com.smapp.sm_app.dto.request.comment.CommentCreateRequest;
import com.smapp.sm_app.dto.request.comment.CommentUpdateRequest;
import com.smapp.sm_app.dto.response.CommentDTO;
import com.smapp.sm_app.entity.Comment;
import com.smapp.sm_app.response_template.ResponseTemplate;
import com.smapp.sm_app.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getComments(
            @RequestParam Optional<Long> postId,
            @RequestParam Optional<Long> userId,
            Pageable pageable) {

        Page<Comment> pageComments = commentService.getComments(postId, userId, pageable);
        Page<CommentDTO> dtoPage = pageComments.map(CommentDTO::new);
        ResponseTemplate<?> responseTemplate = new ResponseTemplate<>("Comments fetched successfully", dtoPage);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<ResponseTemplate<?>> getComment(@PathVariable Long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        CommentDTO commentDTO = new CommentDTO(comment);
        ResponseTemplate<Comment> responseTemplate = new ResponseTemplate("Comment fetched successfully", commentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate<?>> createComment(@RequestBody @Valid CommentCreateRequest commentCreateRequest) {
        Comment comment = commentService.createComment(commentCreateRequest);
        CommentDTO commentDTO = new CommentDTO(comment);
        ResponseTemplate<Comment> responseTemplate = new ResponseTemplate("Comment created successfully", commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseTemplate);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseTemplate<?>> updateComment(@PathVariable Long commentId, @RequestBody @Valid CommentUpdateRequest commentUpdateRequest) {
        Comment comment = commentService.updateComment(commentId, commentUpdateRequest);
        CommentDTO commentDTO = new CommentDTO(comment);
        ResponseTemplate<CommentDTO> responseTemplate = new ResponseTemplate("Comment updated successfully", commentDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseTemplate<?>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        ResponseTemplate<?> responseTemplate = new ResponseTemplate("Comment deleted successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

}
