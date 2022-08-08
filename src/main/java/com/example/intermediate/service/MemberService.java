package com.example.intermediate.service;

import com.example.intermediate.controller.request.LoginRequestDto;
import com.example.intermediate.controller.request.MemberRequestDto;
import com.example.intermediate.controller.request.TokenDto;
import com.example.intermediate.controller.response.MemberResponseDto;
import com.example.intermediate.controller.response.MyPageResponseDto;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.controller.response.mypage.CommentMypageResponseDto;
import com.example.intermediate.controller.response.mypage.PostMypageResponseDto;
import com.example.intermediate.controller.response.mypage.SubCommentMypageResponseDto;
import com.example.intermediate.domain.*;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    //  private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final PostRepository postRepository; //게시글 repo

    private final CommentRepository commentRepository; //댓글 repo

    private final SubCommentRepository subCommentRepository; //대댓글 repo

    private final PostHeartRepository postHeartRepository; //게시글 좋아요 repo

    private final CommentHeartRepository commentHeartRepository; //댓글 좋아요 repo

    private final SubCommentHeartRepository subCommentHeartRepository; //대댓글 좋아요 repo

    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
            return ResponseDto.fail("DUPLICATED_NICKNAME",
                    "중복된 닉네임 입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
            return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        memberRepository.save(member);
        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = isPresentMember(requestDto.getNickname());
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다.");
        }

//    UsernamePasswordAuthenticationToken authenticationToken =
//        new UsernamePasswordAuthenticationToken(requestDto.getNickname(), requestDto.getPassword());
//    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );
    }

//  @Transactional
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//    Member member = tokenProvider.getMemberFromAuthentication();
//    if (null == member) {
//      return ResponseDto.fail("MEMBER_NOT_FOUND",
//          "사용자를 찾을 수 없습니다.");
//    }
//
//    Authentication authentication = tokenProvider.getAuthentication(request.getHeader("Access-Token"));
//    RefreshToken refreshToken = tokenProvider.isPresentRefreshToken(member);
//
//    if (!refreshToken.getValue().equals(request.getHeader("Refresh-Token"))) {
//      return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//    }
//
//    TokenDto tokenDto = tokenProvider.generateTokenDto(member);
//    refreshToken.updateValue(tokenDto.getRefreshToken());
//    tokenToHeaders(tokenDto, response);
//    return ResponseDto.success("success");
//  }

    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }

        return tokenProvider.deleteRefreshToken(member);
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    //마이페이지
    public ResponseDto<?> getMyPage(HttpServletRequest request) {
        Member loginMember = validateMember(request); //맴버의 아이디값, 닉네임, 패스워드

        //좋아요 누른 게시글아이디찾기
        List<PostHeart> postHearts = postHeartRepository.findByMember(loginMember);
        List<PostMypageResponseDto> heartPost = new ArrayList<>();
        for (PostHeart postHeart : postHearts) {
            //좋아요 누른 게시글찾기
            Post post = postHeart.getPost();
            heartPost.add(
                    PostMypageResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .createdAt(post.getCreatedAt())
                            .build()
            );
        }
        //좋아요 누른 댓글아이디찾기
        List<CommentHeart> commentHearts = loginMember.getCommentHearts();
        List<CommentMypageResponseDto> heartComment = new ArrayList<>();
        for (CommentHeart commentHeart : commentHearts) {
            //좋아요 누른 댓글찾기
            Comment comment = commentHeart.getComment();
            heartComment.add(
                    CommentMypageResponseDto.builder()
                            .commentId(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .build()
            );
        }

        //좋아요 누른 대댓글아이디찾기
        List<SubCommentHeart> subCommentHearts = subCommentHeartRepository.findByMember(loginMember);
        List<SubCommentMypageResponseDto> heartSubComment = new ArrayList<>();
        for ( SubCommentHeart subCommentHeart : subCommentHearts) {
            //좋아요 누른 대댓글찾기
            SubComment subComment = subCommentHeart.getSubComment();
            heartSubComment.add(
                    SubCommentMypageResponseDto.builder()
                            .subCommentId(subComment.getId())
                            .content(subComment.getContent())
                            .createdAt(subComment.getCreatedAt())
                            .build()
            );
        }


        List<Post> postList = postRepository.findByMember_Id(loginMember.getId());
        List<PostMypageResponseDto> myPostsResponseDtoList = new ArrayList<>();
        //내가 쓴 게시글 리스트 생성
        for (Post post : postList) {
            myPostsResponseDtoList.add(
                    PostMypageResponseDto.builder()
                            .postId(post.getId())
                            .title(post.getTitle())
                            .createdAt(post.getCreatedAt())
                            .build()
            );
        }
        List<Comment> commentList = commentRepository.findByMember_Id(loginMember.getId());
        List<CommentMypageResponseDto> myCommentsResponseDtoList = new ArrayList<>();
        //내가 쓴 댓글 리스트 생성
        for (Comment comment : commentList) {
            myCommentsResponseDtoList.add(
                    CommentMypageResponseDto.builder()
                            .commentId(comment.getId())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .build()
            );
        }
        List<SubComment> subCommentList = subCommentRepository.findByMember_Id(loginMember.getId());
        List<SubCommentMypageResponseDto> mySubCommentsResponseDtoList = new ArrayList<>();
        //내가 쓴 대댓글 리스트 생성
        for(SubComment subComment : subCommentList) {
            mySubCommentsResponseDtoList.add(
                    SubCommentMypageResponseDto.builder()
                            .subCommentId(subComment.getId())
                            .content(subComment.getContent())
                            .createdAt(subComment.getCreatedAt())
                            .build()
            );
        }

        MyPageResponseDto responseDto = MyPageResponseDto.builder()
                .heartPostResponse(heartPost)
                .heartCommentResponse(heartComment)
                .heartSubCommentResponse(heartSubComment)
                .myPosts(myPostsResponseDtoList)
                .myComments(myCommentsResponseDtoList)
                .mySubComments(mySubCommentsResponseDtoList)
                .build();

        return ResponseDto.success(responseDto);
    }

    //리프레쉬 토큰값이 있는지 확인하는 메서드
    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
