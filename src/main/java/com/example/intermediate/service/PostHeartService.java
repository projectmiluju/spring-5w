package com.example.intermediate.service;

import com.example.intermediate.controller.request.PostHeartRequestDto;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.PostHeart;
import com.example.intermediate.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;

    public PostHeart createPostHeart(PostHeartRequestDto requestDto, UserDetails userDetails) {

        Optional<PostHeart> optionalPostHeart = postHeartRepository.findByPostIdAndMemberId(
                requestDto.getPostId(), requestDto.getMemberId());

        if (optionalPostHeart.isPresent()) {
            return null;
        }

        PostHeart postHeart = new PostHeart(requestDto.getPostId(), requestDto.getMemberId());

        return postHeartRepository.save(postHeart);
    }

    public String deletePostHeart(PostHeartRequestDto requestDto, UserDetails userDetails) {
        Optional<PostHeart> optionalPostHeart = postHeartRepository.findByPostIdAndMemberId(
                requestDto.getPostId(), requestDto.getMemberId());
        if (optionalPostHeart.isPresent()) {
            postHeartRepository.delete(optionalPostHeart.get());
            return "success";
        }
        return "fail";
    }
}
