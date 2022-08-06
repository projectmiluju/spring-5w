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

    // 대댓글 생성
    @RequestMapping(value = "/api/auth/sub-comment", method = RequestMethod.POST)
    public ResponseDto<?> createSubComment(@RequestBody SubCommentRequestDto requestDto,
                                           HttpServletRequest request) {
        return subCommentService.createSubComment(requestDto, request);
    }

    // 대댓글 수정
    @PutMapping("/api/auth/sub-comment/{subCommentId}") //대댓글의 url주소
    public ResponseDto<?> updateSubComment(@PathVariable Long subCommentId, @RequestBody SubCommentRequestDto requestDto,
                                           HttpServletRequest request) { //url id값을 받고 ,리퀘스트바디에 값을 requestDto에 저장
        //header에 있는 토큰 값을 저장
        //service에 있는 메서드로 id,requestdto,request 값을 보냄
        return subCommentService.updateSubComment(subCommentId, requestDto, request);
    }

    // 대댓글 삭제
    @DeleteMapping("/api/auth/sub-comment/{subCommentId}")
    public ResponseDto<?> deleteSubComment(@PathVariable Long subCommentId, HttpServletRequest request){
        return subCommentService.deleteSubComment(subCommentId,request);
    }
}
