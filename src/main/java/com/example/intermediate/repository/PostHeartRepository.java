package com.example.intermediate.repository;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Optional<PostHeart> findByPostAndMember(Post post, Member member);

    Optional<PostHeart> findByPost_IdAndMember_Id(Long postId, Long memberId);

    List<PostHeart> findByMember(Member member);
}
