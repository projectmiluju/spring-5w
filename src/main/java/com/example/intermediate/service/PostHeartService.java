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
        // 게시글이 존재하는 지 여부



        // 사용자가 같은 게시글에 중복으로 눌렀는지 검사
        Optional<PostHeart> optionalPostHeart = postHeartRepository.findByPostIdAndMemberId(
                requestDto.getPostId(), requestDto.getMemberId());

        if (optionalPostHeart.isPresent()) {
            return null;
        }

        PostHeart postHeart = new PostHeart(requestDto);

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
