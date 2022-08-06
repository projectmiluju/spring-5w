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

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    public PostHeart createPostHeart(PostHeartRequestDto requestDto, UserDetails userDetails) {
        // 게시글이 존재하는 지 여부
         Optional<Post> postCheck = postRepository.findById(requestDto.getPostId());
         if(postCheck.isEmpty()){
             throw new IllegalArgumentException("게시글이 없습니다!");
         }
        
         //회원인지 확인
         Optional<Member> membercheck = memberRepository.findById(requestDto.getMemberId());
         if (membercheck.isEmpty()){
             throw new IllegalArgumentException("회원이 아닙니다");
         }

         if(!userDetails.getUsername().equals(membercheck.get().getNickname())){
             throw new IllegalArgumentException("일치하는 회원이 아닙니다");
         }


        // 사용자가 같은 게시글에 중복으로 눌렀는지 검사
        Optional<PostHeart> optionalPostHeart = postHeartRepository.findByPostIdAndMemberId(
                requestDto.getPostId(), requestDto.getMemberId());

        if (optionalPostHeart.isPresent()) {
            throw new IllegalArgumentException("좋아요를 누르셨습니다!");
        }

        PostHeart postHeart = new PostHeart(requestDto);

        return postHeartRepository.save(postHeart);
    }

    public ResponseDto<?> deletePostHeart(PostHeartRequestDto requestDto, UserDetails userDetails) {

        Optional<PostHeart> optionalPostHeart = postHeartRepository.findByPostIdAndMemberId(
                requestDto.getPostId(), requestDto.getMemberId());
        if (optionalPostHeart.isPresent()){

            Optional<Member> membercheck = memberRepository.findById(requestDto.getMemberId());

            if(!userDetails.getUsername().equals(membercheck.get().getNickname())){
                throw new IllegalArgumentException("일치하는 회원이 아닙니다");
            }

            postHeartRepository.delete(optionalPostHeart.get());

            return ResponseDto.success("좋아요를 지우셨습니다.");
        }

        return ResponseDto.fail("BAD_REQUEST","좋아요를 누르지 않으셨습니다.");
    }
}
