package com.smapp.sm_app.repository;

import com.smapp.sm_app.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPostIdAndUserId(Long postId, Long userId, Pageable pageable);

    Page<Comment> findByPostId(Long postId,Pageable pageable);

    Page<Comment> findByUserId(Long userId, Pageable pageable);
}
