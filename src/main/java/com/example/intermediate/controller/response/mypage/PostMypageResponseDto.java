package com.example.intermediate.controller.response.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostMypageResponseDto {
    private Long postId;
    private String title;
    private LocalDateTime createdAt;
}
