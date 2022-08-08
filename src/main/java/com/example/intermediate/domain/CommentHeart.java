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
     @ManyToOne(fetch = FetchType.LAZY)  //회원하나에 댓글 여러개
    private Member member; //연관관계를 위해 객체 생성

     @JoinColumn(name = "comment_id", nullable = false)
     @ManyToOne(fetch = FetchType.LAZY) //댓글하나에 좋아요 여러개
    private Comment comment; //연관관계를 위해 객체생성

//    public CommentHeart(CommentHeartRequestDto requestDto) {
//        this.memberId = requestDto.getMemberId();
//        this.commentId = requestDto.getCommentId();
//    }

}
