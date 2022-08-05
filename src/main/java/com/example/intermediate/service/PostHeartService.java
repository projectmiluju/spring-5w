package com.example.intermediate.service;

import com.example.intermediate.controller.request.PostHeartRequestDto;
import com.example.intermediate.domain.PostHeart;
import com.example.intermediate.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;

    public PostHeart createPostHeart(PostHeartRequestDto requestDto, UserDetails userDetails) {
        PostHeart postHeart = new PostHeart(requestDto.getPostId(), requestDto.getMemberId());
        return postHeartRepository.save(postHeart);
    }
}
