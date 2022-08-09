package com.example.intermediate.scheduler;

import com.example.intermediate.domain.Post;
import com.example.intermediate.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostScheduler {

    private final PostRepository postRepository;

    // 매일 새벽 1시
    @Scheduled(cron = "0 0 1 * * *")
    public void garbageCollect() {
        List<Post> posts = postRepository.findNoComment();

        for (Post post : posts) {
            log.info("게시글 [id: " + post.getId() + ", title: " + post.getTitle() + "] 에 댓글이 없어 삭제되었습니다.");
            postRepository.delete(post);
        }
    }

//    // 매일 새벽 1시
//    @Scheduled(cron = "0 0 1 * * *")
//    @Transactional
//    public void garbageCollectCheckComment() {
//        List<Post> posts = postRepository.findAll();
//
//        for (Post post : posts) {
//            if (post.getComments().isEmpty()) {
//                log.info("게시글 [id: " + post.getId() + ", title: " + post.getTitle() + "] 에 댓글이 없어 삭제되었습니다.");
//                postRepository.delete(post);
//            }
//        }
//    }
}
