package com.example.intermediate.domain;


import com.example.intermediate.controller.request.SubCommentHeartRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentHeart extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "subcomment_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private SubComment subComment;

    public SubCommentHeart(Member member, SubComment subComment) {
        this.member = member;
        this.subComment = subComment;
    }
}
