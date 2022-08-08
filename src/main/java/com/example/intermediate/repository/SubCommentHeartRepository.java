package com.example.intermediate.repository;

import com.example.intermediate.domain.SubCommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCommentHeartRepository extends JpaRepository<SubCommentHeart, Long> {
    Optional<SubCommentHeart> findBySubCommentIdAndMemberId(Long subCommentId, Long memberId);

    List<SubCommentHeart> findBySubCommentId(Long subCommentId);

    long countBySubCommentId(Long subCommentId);
}
