package com.example.intermediate.repository;

import com.example.intermediate.domain.CommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Optional<CommentHeart> findByCommentIdAndMemberId(Long commentId, Long memberId);

    List<CommentHeart> findByCommentId(Long commentId);

    long countByCommentId(Long commentId);

    List<CommentHeart> findByMemberId(Long memberId);
}