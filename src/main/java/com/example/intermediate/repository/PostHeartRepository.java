package com.example.intermediate.repository;

import com.example.intermediate.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Optional<PostHeart> findByPostIdAndMemberId(Long postId, Long memberId);

    List<PostHeart> findByPostId(Long postId);

    long countByPostId(Long postId);

    List<PostHeart> findByMemberId(Long memberId);
}
