package com.example.intermediate.repository;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.domain.SubCommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCommentHeartRepository extends JpaRepository<SubCommentHeart, Long> {
    Optional<SubCommentHeart> findBySubCommentAndMember(SubComment subComment, Member member);

    Optional<SubCommentHeart> findBySubComment_IdAndMember_Id(Long subCommentId,Long memberId);

    List<SubCommentHeart> findBySubComment_Id(Long subCommentId);

    List<SubCommentHeart> findByMember(Member member);
}
