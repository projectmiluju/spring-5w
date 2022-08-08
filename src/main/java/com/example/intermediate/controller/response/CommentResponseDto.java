package com.example.intermediate.controller.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String author;
  private String content;
  //댓글 좋아요
  private int commentHeartCount;

  //대댓글 리스트
  private List<SubCommentResponseDto> subComments;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
