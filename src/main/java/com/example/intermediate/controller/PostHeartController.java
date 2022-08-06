package com.example.intermediate.controller;

import com.example.intermediate.controller.request.PostHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.PostHeart;
import com.example.intermediate.service.PostHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostHeartController {

    private final PostHeartService postHeartService;

    @PostMapping("/api/auth/postheart")
    public ResponseDto<?> createPostHeart(@RequestBody PostHeartRequestDto requestDto,
                                      @AuthenticationPrincipal UserDetails userDetails){
//        PostHeart postHeart = postHeartService.createPostHeart(requestDto,userDetails);

//        if (postHeart == null) {
//            return ResponseDto.fail("POST_HEART_EXIST",
//                    "같은 게시글에 좋아요를 중복하여 누를 수 없습니다.");
//        }

//        return ResponseDto.success(postHeart);
        return postHeartService.createPostHeart(requestDto,userDetails); //신규코드
    }

    @DeleteMapping("/api/auth/postheart")
    public ResponseDto<?> deletePostHeart(@RequestBody PostHeartRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetails userDetails){

        return postHeartService.deletePostHeart(requestDto,userDetails);
    }
}
