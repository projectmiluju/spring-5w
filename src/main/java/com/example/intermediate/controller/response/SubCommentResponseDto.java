package com.example.intermediate.controller.response;

import com.example.intermediate.domain.SubComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentResponseDto {
    private Long id;
    private String author;
    private String content;
    //대댓글 좋아요
    private int subCommentHeartCount;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public SubCommentResponseDto(SubComment subComment) {
        this.id = subComment.getId();
        this.author = subComment.getMember().getNickname();
        this.content = subComment.getContent();
        this.createdAt = subComment.getCreatedAt();
        this.modifiedAt = subComment.getModifiedAt();
    }
}
