package com.example.intermediate.repository;

import com.example.intermediate.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc();

    List<Post> findByMember_Id(Long MemberId);

    @Query(value = "Select p from Post p Left Join Comment c On p.id = c.post.id where c.id is null")
    List<Post> findNoComment();
}
