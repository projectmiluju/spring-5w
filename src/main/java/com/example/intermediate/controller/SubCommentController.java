package com.example.intermediate.controller;

import com.example.intermediate.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubCommentController {

    private final SubCommentService subCommentService;
}
