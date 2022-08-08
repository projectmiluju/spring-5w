package com.example.intermediate.controller;

import com.example.intermediate.controller.request.CommentHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.CommentHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentHeartController {

    private final CommentHeartService commentHeartService;

    @PostMapping("/api/auth/commentheart") // 댓글 좋아요
    public ResponseDto<?> createCommentHeart(@RequestBody CommentHeartRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails){
        return commentHeartService.creatCommentHeart(requestDto,userDetails);
    }

    @DeleteMapping("/api/auth/commentheart") // 댓글 좋아요 취소
    public ResponseDto<?> deleteCommentHeart(@RequestBody CommentHeartRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetails userDetails){

        return commentHeartService.deleteCommentHeart(requestDto,userDetails);
    }

}
