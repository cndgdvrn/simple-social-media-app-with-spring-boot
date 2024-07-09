package com.smapp.sm_app.service;

import com.smapp.sm_app.dto.request.like.LikeCreateRequest;
import com.smapp.sm_app.entity.Like;
import com.smapp.sm_app.entity.Post;
import com.smapp.sm_app.entity.User;
import com.smapp.sm_app.exception.LikeNotFoundException;
import com.smapp.sm_app.repository.LikeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostService postService;
    private final UserService userService;

    public Page<Like> getLikes(Optional<Long> postId, Optional<Long> userId, Pageable pageable) {
        if(postId.isPresent() && userId.isPresent()){
            return likeRepository.findByPostIdAndUserId(postId.get(), userId.get(), pageable);
        } else if(postId.isPresent()){
            return likeRepository.findByPostId(postId.get(),pageable);
        } else if(userId.isPresent()){
            return likeRepository.findByUserId(userId.get(),pageable);
        }
        return likeRepository.findAll(pageable);
    }

    @Transactional
    public Like createAndDeleteLike(LikeCreateRequest likeCreateRequest) {
        User user = userService.getUserById(likeCreateRequest.getUserId());
        Post post = postService.getPostById(likeCreateRequest.getPostId());
        boolean exists = likeRepository.existsByPostIdAndUserId(likeCreateRequest.getPostId(), likeCreateRequest.getUserId());
        if(exists){
            likeRepository.deleteByPostIdAndUserId(likeCreateRequest.getPostId(), likeCreateRequest.getUserId());
            post.setLikesCount(post.getLikesCount()-1);
            return null;
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        post.setLikesCount(post.getLikesCount()+1);
        return likeRepository.save(like);
    }

    public Like getLikeById(Long id) {
        return likeRepository.findById(id).orElseThrow(()-> new LikeNotFoundException("Like not found with id: "+id));
    }
}


