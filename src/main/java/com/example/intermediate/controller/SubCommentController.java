package com.example.intermediate.controller;

import com.example.intermediate.controller.request.SubCommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Validated
@RestController
@RequiredArgsConstructor
public class SubCommentController {

    private final SubCommentService subCommentService;

    // 댓글 아이디로 댓글에 달린 대댓글 전부 가져오기
    @GetMapping("/api/sub-comment")
    public ResponseDto<?> getAllSubCommentsByCommentId(
            @RequestBody SubCommentRequestDto requestDto
    ) {
        return subCommentService.getAllSubCommentsByCommentId(requestDto);
    }

    @RequestMapping(value = "/api/auth/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createSubComment(@RequestBody SubCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return subCommentService.createSubComment(requestDto, request);
    }
}
