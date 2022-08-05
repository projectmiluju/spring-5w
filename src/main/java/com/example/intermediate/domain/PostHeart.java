package com.example.intermediate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PostHeart extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // @JoinColumn(name = "member_id", nullable = false)
   // @ManyToOne(fetch = FetchType.LAZY)
    private Long memberId;

   // @JoinColumn(name = "post_id", nullable = false)
   // @ManyToOne(fetch = FetchType.LAZY)
    private Long postId;

    public PostHeart(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
