package com.example.intermediate.controller.response;

import com.example.intermediate.controller.response.mypage.CommentMypageResponseDto;
import com.example.intermediate.controller.response.mypage.PostMypageResponseDto;
import com.example.intermediate.controller.response.mypage.SubCommentMypageResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {
    // 사용자가 작성한 게시글/댓글/대댓글
    private List<PostMypageResponseDto> myPosts;
    private List<CommentMypageResponseDto> myComments;
    private List<SubCommentMypageResponseDto> mySubComments;

    // 좋아요한 게시글/댓글/대댓글
}
