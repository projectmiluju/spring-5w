package com.example.intermediate.service;

import com.example.intermediate.controller.request.SubCommentRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.response.SubCommentResponseDto;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.repository.CommentRepository;
import com.example.intermediate.repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCommentService {

    private final SubCommentRepository subCommentRepository;

    private final CommentRepository commentRepository;

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
}
