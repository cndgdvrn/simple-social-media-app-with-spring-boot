package com.smapp.sm_app.controller;

import com.smapp.sm_app.dto.request.post.PostCreateRequest;
import com.smapp.sm_app.dto.request.post.PostUpdateRequest;
import com.smapp.sm_app.dto.response.PostDTO;
import com.smapp.sm_app.entity.Post;
import com.smapp.sm_app.response_template.ResponseTemplate;
import com.smapp.sm_app.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getPosts(@RequestParam(required = false) Optional<Long> userId) {
        List<Post> postList = postService.getALlPosts(userId);
        List<PostDTO> collected = postList.stream().map(PostDTO::new).toList();
        ResponseTemplate<List<PostDTO>> responseTemplate = new ResponseTemplate<>("Posts fetched successfully", collected);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseTemplate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<?>> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        PostDTO postDTO = new PostDTO(post);
        ResponseTemplate<PostDTO> responseTemplate = new ResponseTemplate<>("Post fetched successfully", postDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate<?>> createPost(@RequestBody @Valid PostCreateRequest postCreateRequest) {
        Post post = postService.createPost(postCreateRequest);
        PostDTO postDTO = new PostDTO(post);
        ResponseTemplate<PostDTO> responseTemplate = new ResponseTemplate<>("Post created successfully", postDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseTemplate);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ResponseTemplate<?>> updatePost(
            @PathVariable Long postId, @RequestBody @Valid PostUpdateRequest postUpdateRequest) {
        Post post = postService.updatePost(postId, postUpdateRequest);
        PostDTO postDTO = new PostDTO(post);
        ResponseTemplate<PostDTO> responseTemplate = new ResponseTemplate<>("Post updated successfully", postDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseTemplate<?>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        ResponseTemplate<?> responseTemplate = new ResponseTemplate<>("Post deleted successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }
}
