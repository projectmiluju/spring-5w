package com.example.intermediate.service;

import com.example.intermediate.controller.request.CommentHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.*;
import com.example.intermediate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentHeartService {

    private final CommentHeartRepository commentHeartRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ResponseDto<?> creatCommentHeart(CommentHeartRequestDto requestDto, UserDetails userDetails) {
        // 댓글이 존재하는지 여부
        Optional<Comment> commentCheck = commentRepository.findById(requestDto.getCommentId());
        if (commentCheck.isEmpty()) {
            throw new IllegalArgumentException("댓글이 없습니다!");
        }
        //회원인지 확인
        Optional<Member> membercheck = memberRepository.findById(requestDto.getMemberId());
        if (membercheck.isEmpty()) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        // 회원이 일치하는지 확인
        if (!userDetails.getUsername().equals(membercheck.get().getNickname())) {
            throw new IllegalArgumentException("일치하는 회원이 아닙니다");
        }
        // 사용자가 같은 댓글에 중복으로 눌렀는지 검사
        Optional<CommentHeart> optionalCommentHeart = commentHeartRepository.findByCommentAndMember(
                commentCheck.get(), membercheck.get());
        //optionalCommentHeart에서 값이 없을때... (중복일때)
        if (optionalCommentHeart.isPresent()) { //값이 있을때.. true
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다");
        }

        CommentHeart commentHeart = CommentHeart.builder()
                .comment(commentCheck.get())
                .member(membercheck.get())
                .build();
        commentHeartRepository.save(commentHeart);
        return ResponseDto.success("좋아요를 등록했습니다.");
    }

    @Transactional
    public ResponseDto<?> deleteCommentHeart(CommentHeartRequestDto requestDto, UserDetails userDetails) {

        Optional<CommentHeart> optionalCommentHeart = commentHeartRepository.findByComment_IdAndMember_Id(
                requestDto.getCommentId(), requestDto.getMemberId());
        //좋아요가 눌려있지 않다면
        if (optionalCommentHeart.isEmpty()) {
            throw new IllegalArgumentException("좋아요를 누르지 않으셨습니다.");
        }
        Member membercheck = optionalCommentHeart.get().getMember();
        //누른게 로그인한 사람이 아니라면
        if (!userDetails.getUsername().equals(membercheck.getNickname())) {
            throw new IllegalArgumentException("일치하는 회원이 아닙니다");
        }
        commentHeartRepository.delete(optionalCommentHeart.get());
        return ResponseDto.success("좋아요를 지우셨습니다.");
    }
}
