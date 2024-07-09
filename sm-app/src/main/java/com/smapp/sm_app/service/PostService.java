package com.smapp.sm_app.service;

import com.smapp.sm_app.dto.request.post.PostCreateRequest;
import com.smapp.sm_app.dto.request.post.PostUpdateRequest;
import com.smapp.sm_app.entity.Post;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.exception.PostNotFoundException;
import com.smapp.sm_app.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public List<Post> getALlPosts(Optional<Long> userId) {
        if(userId.isPresent()){
            userService.getUserById(userId.get());
            return postRepository.findAllByUserId(userId.get());
        }
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("Post not found with id: "+id));
    }

    public Post createPost(PostCreateRequest postCreateRequest) {
        User userInDB = userService.getUserById(postCreateRequest.getUserId());
        Post post = new Post();
        post.setUser(userInDB);
        post.setTitle(postCreateRequest.getTitle());
        post.setText(postCreateRequest.getText());
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Post post = getPostById(postId);
        post.setTitle(postUpdateRequest.getTitle());
        post.setText(postUpdateRequest.getText());
        return postRepository.save(post);
    }

    public void deletePost(Long postId) {
        getPostById(postId);
        postRepository.deleteById(postId);
    }
}
