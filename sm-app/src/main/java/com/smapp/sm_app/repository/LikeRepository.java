package com.smapp.sm_app.repository;

import com.smapp.sm_app.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Page<Like> findByPostIdAndUserId(Long postId, Long userId, Pageable pageable);

    Page<Like> findByPostId(Long postId, Pageable pageable);

    Page<Like> findByUserId(Long userId, Pageable pageable);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);
}
