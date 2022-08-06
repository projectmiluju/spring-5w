package com.example.intermediate.service;


import com.example.intermediate.controller.request.SubCommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.response.SubCommentResponseDto;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCommentService {

    private final SubCommentRepository subCommentRepository; //대댓글 레포지토리 생성

    private final TokenProvider tokenProvider; //토큰을 생성하는

    private final CommentRepository commentRepository; //댓글 레포지토리 생성

    private final CommentService commentService;

    @Transactional
    public ResponseDto<?> createSubComment(SubCommentRequestDto requestDto, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment = commentService.isPresentComment(requestDto.getCommentId());
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 댓글 id 입니다.");
        }

        SubComment subComment = SubComment.builder()
                .member(member)
                .comment(comment)
                .content(requestDto.getContent())
                .build();
        subCommentRepository.save(subComment);
        return ResponseDto.success(
                SubCommentResponseDto.builder()
                        .id(subComment.getId())
                        .author(subComment.getMember().getNickname())
                        .content(subComment.getContent())
                        .createdAt(subComment.getCreatedAt())
                        .modifiedAt(subComment.getModifiedAt())
                        .build()
        );
    }





    // 댓글에 달린 전체 대댓글 조회
    public ResponseDto<?> getAllSubCommentsByCommentId(SubCommentRequestDto requestDto) {
        // requestDto를 통해 넘어온 commentId를 이용하여 Comment가 존재하는지 확인
        Long commentId = requestDto.getCommentId();
        Comment comment = commentRepository.findById(requestDto.getCommentId())
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다. commentId: " + commentId)
                );

        // 결과를 저장할 리스트
        List<SubCommentResponseDto> responseDtoList = new ArrayList<>();
        // Comment에 연관관계로 저장된 SubComment 리스트를 불러와서 하나씩 실행
        for (SubComment subComment : comment.getSubComments()) {
            // SubComment -> SubCommentResponseDto 변환
            SubCommentResponseDto responseDto = SubCommentResponseDto.builder()
                    .id(subComment.getId())
                    .author(comment.getMember().getNickname())
                    .content(subComment.getContent())
                    .createdAt(subComment.getCreatedAt())
                    .modifiedAt(subComment.getModifiedAt())
                    .build();
            // 결과 배열에 저장
            responseDtoList.add(responseDto);
        }

        return ResponseDto.success(responseDtoList);
    }

    

    //대댓글 수정메서드
    public ResponseDto<?> updateSubComment(Long id, SubCommentRequestDto requestDto, HttpServletRequest request) {
        //헤더에 리프레쉬토큰값이 없을떄
        if(null == request.getHeader("Refresh-Token")){ //HttpServletRequest 에 refresh token이 없으면 트루
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        //헤더에
        if(null == request.getHeader("Authorization")){ //HttpServletRequest 에 Authorization(Acees토큰)이 없으면 트루
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        // 댓글DB에 commentId가 있는지 확인
        Optional<Comment> optionalComment = commentRepository.findBycommentId(id);
        if(optionalComment.isEmpty()){ //commentId로 검색한 commentDB에 데이터가 없으면 true
            throw new IllegalArgumentException("댓글이 없습니다"); //Exception 발생
        }
        // 대댓글 db에서 commentId값이 있는지 확인
        Optional<SubComment> subComment = subCommentRepository.findById(id); //
        subComment.get().update(requestDto); // requestDto에 있는 값을 대댓글DB에 수정


        SubCommentResponseDto responseDto = new SubCommentResponseDto(subComment.get()); //대댓글 리스폰스객체에 subComment데이터 보냄
        return ResponseDto.success(responseDto); //수정된 데이터가 있는 responseDTO에 저장후 리턴
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
