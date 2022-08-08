package com.example.intermediate.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentRequestDto {
    private Long commentId; //댓글아이디
    private String content; //대댓글 작성글
}
