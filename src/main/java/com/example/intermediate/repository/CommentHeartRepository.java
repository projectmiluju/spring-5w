package com.example.intermediate.repository;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.CommentHeart;
import com.example.intermediate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Optional<CommentHeart> findByCommentAndMember(Comment comment, Member member); //연관관계를 사용을 위한 repo

    List<CommentHeart> findByCommentId(Long commentId);

    long countByCommentId(Long commentId);

    List<CommentHeart> findByMemberId(Long memberId);


    Optional<CommentHeart> findByComment_IdAndMember_Id(Long commentId, Long memberId); //삭제를 위한 repo

    List<CommentHeart> findByMember(Member member); //연관관계 생성 수정
}