package com.example.intermediate.controller.response;

import com.example.intermediate.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostListResponseDto {
    private Long id;
    private String title;
    private String author;
    private long postHeartCount;
    private int commentCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostListResponseDto(Post post, long postHeartCount) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getMember().getNickname();
        this.postHeartCount = postHeartCount;
        this.commentCount = post.getComments().size();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
