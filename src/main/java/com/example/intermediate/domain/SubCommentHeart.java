package com.example.intermediate.domain;


import com.example.intermediate.controller.request.SubCommentHeartRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class SubCommentHeart extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @JoinColumn(name = "member_id", nullable = false)
    // @ManyToOne(fetch = FetchType.LAZY)
    private Long memberId;

    // @JoinColumn(name = "post_id", nullable = false)
    // @ManyToOne(fetch = FetchType.LAZY)
    private Long subCommentId;

    public SubCommentHeart(SubCommentHeartRequestDto requestDto) {
        this.memberId = requestDto.getMemberId();
        this.subCommentId = requestDto.getSubCommentId();
    }

}
