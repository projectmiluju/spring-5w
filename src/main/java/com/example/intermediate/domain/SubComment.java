package com.example.intermediate.domain;

import com.example.intermediate.controller.request.SubCommentRequestDto;
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
public class SubComment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false) //연관관계
    @ManyToOne(fetch = FetchType.LAZY) // 맴버하나에 대댓글 여러개
    private Member member;

    @JoinColumn(name = "comment_id", nullable = false) //연관관계
    @ManyToOne(fetch = FetchType.LAZY) //댓글 하나에 대댓글 여러개
    private Comment comment;

    @Column(nullable = false)
    private String content;

    public void update(SubCommentRequestDto subCommentRequestDto) {
        this.content = subCommentRequestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}