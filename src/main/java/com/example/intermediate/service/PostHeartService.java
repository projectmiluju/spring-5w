package com.example.intermediate.service;

import com.example.intermediate.controller.request.PostHeartRequestDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.PostHeart;
import com.example.intermediate.repository.MemberRepository;
import com.example.intermediate.repository.PostHeartRepository;
import com.example.intermediate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    // 게시글 좋아요 누르기, 연관관계 자동 설정을 위한 Transactional 추가
    @Transactional
    public ResponseDto<?> createPostHeart(PostHeartRequestDto requestDto, UserDetails userDetails) {
        // 게시글이 존재하는 지 여부
        Optional<Post> postCheck = postRepository.findById(requestDto.getPostId());
        if (postCheck.isEmpty()) {
            throw new IllegalArgumentException("게시글이 없습니다!");
        }

        //회원인지 확인
        Optional<Member> membercheck = memberRepository.findById(requestDto.getMemberId());
        if (membercheck.isEmpty()) {
            throw new IllegalArgumentException("회원이 아닙니다");
        }
        //회원이 일치하는지 확인하기
        if (!userDetails.getUsername().equals(membercheck.get().getNickname())) {
            throw new IllegalArgumentException("일치하는 회원이 아닙니다");
        }

        // 사용자가 같은 게시글에 중복으로 눌렀는지 검사
        Optional<PostHeart> optionalPostHeart = postHeartRepository.findByPostAndMember(
                postCheck.get(), membercheck.get());
        //optionalPostHeart에서 값이 없을때... (중복일때)
        if (optionalPostHeart.isPresent()) { //값이 있을때.. true
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다");
        }

        // PostHeart 객체 생성
        PostHeart postHeart = PostHeart.builder()
                .post(postCheck.get())
                .member(membercheck.get())
                .build();
        postHeartRepository.save(postHeart);
        return ResponseDto.success("좋아요를 등록했습니다.");
    }

    public ResponseDto<?> deletePostHeart(PostHeartRequestDto requestDto, UserDetails userDetails) {

        Optional<PostHeart> optionalPostHeart = postHeartRepository.findByPost_IdAndMember_Id(
                requestDto.getPostId(), requestDto.getMemberId());
        //좋아요가 눌려있지 않다면
        if (optionalPostHeart.isEmpty()) {
            throw new IllegalArgumentException("좋아요를 누르지 않으셨습니다.");
        }
        Optional<Member> membercheck = memberRepository.findById(requestDto.getMemberId());
        //누른게 로그인한 사람이 아니라면
        if (!userDetails.getUsername().equals(membercheck.get().getNickname())) {
            throw new IllegalArgumentException("일치하는 회원이 아닙니다");
        }
        postHeartRepository.delete(optionalPostHeart.get());
        return ResponseDto.success("좋아요를 지우셨습니다.");
    }
}
