package com.example.intermediate.service;


import com.example.intermediate.repository.SubCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCommentService {
    private final SubCommentRepository subCommentRepository;
}
