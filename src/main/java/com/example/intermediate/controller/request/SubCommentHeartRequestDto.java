package com.example.intermediate.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentHeartRequestDto {

    private Long subCommentId;

    private Long memberId;
}
