package com.smapp.sm_app.controller;

import com.smapp.sm_app.dto.request.like.LikeCreateRequest;
import com.smapp.sm_app.dto.response.LikeDTO;
import com.smapp.sm_app.entity.Like;
import com.smapp.sm_app.response_template.ResponseTemplate;
import com.smapp.sm_app.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getLikes(
            @RequestParam Optional<Long> postId,
            @RequestParam Optional<Long> userId,
            @PageableDefault(size=1000) Pageable pageable) {

        Page<Like> likePage = likeService.getLikes(postId, userId, pageable);
        Page<LikeDTO> likeDTOS = likePage.map(LikeDTO::new);
        ResponseTemplate<?> responseTemplate = new ResponseTemplate<>("Likes fetched successfully", likeDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate<?>> getLike(@PathVariable Long id) {
        Like like = likeService.getLikeById(id);
        LikeDTO likeDTO = new LikeDTO(like);
        ResponseTemplate<LikeDTO> responseTemplate = new ResponseTemplate<>("Like fetched successfully", likeDTO);
        return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate<?>> createLike(@RequestBody @Valid LikeCreateRequest likeCreateRequest) {
        Like like = likeService.createAndDeleteLike(likeCreateRequest);
        if(like == null){
            ResponseTemplate<?> responseTemplate = new ResponseTemplate<>("Like deleted successfully", null);
            return ResponseEntity.status(HttpStatus.OK).body(responseTemplate);
        }
        LikeDTO likeDTO = new LikeDTO(like);
        ResponseTemplate<LikeDTO> responseTemplate = new ResponseTemplate<>("Like created successfully", likeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseTemplate);
    }


}
