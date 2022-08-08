package com.example.intermediate.controller;

import com.example.intermediate.controller.request.CommentHeartRequestDto;
import com.example.intermediate.controller.request.SubCommentHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.SubCommentHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubCommentHeartController {

    private final SubCommentHeartService subCommentHeartService;

    @PostMapping("/api/auth/sub-commentheart") // 대댓글 좋아요
    public ResponseDto<?> createSubCommentHeart(@RequestBody SubCommentHeartRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails){
        return subCommentHeartService.creatSubCommentHeart(requestDto,userDetails);
    }

    @DeleteMapping("/api/auth/sub-commentheart") // 대댓글 좋아요 취소
    public ResponseDto<?> deleteSubCommentHeart(@RequestBody SubCommentHeartRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetails userDetails){

        return subCommentHeartService.deleteSubCommentHeart(requestDto,userDetails);
    }

}
