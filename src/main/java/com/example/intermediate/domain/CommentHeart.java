package com.example.intermediate.domain;




import com.example.intermediate.controller.request.CommentHeartRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CommentHeart extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @JoinColumn(name = "member_id", nullable = false)
     @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

     @JoinColumn(name = "comment_id", nullable = false)
     @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

//    public CommentHeart(CommentHeartRequestDto requestDto) {
//        this.memberId = requestDto.getMemberId();
//        this.commentId = requestDto.getCommentId();
//    }

}
