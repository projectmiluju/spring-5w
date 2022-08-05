package com.example.intermediate.controller;

import com.example.intermediate.controller.request.PostHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.PostHeart;
import com.example.intermediate.service.PostHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostHeartController {

    private final PostHeartService postHeartService;

    @PostMapping("/api/auth/postheart")
    public ResponseDto<?> createHeart(@RequestBody PostHeartRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails){
        PostHeart postHeart = postHeartService.createPostHeart(requestDto,userDetails);

        return ResponseDto.success(postHeart);
    }
}
