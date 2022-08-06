package com.example.intermediate.exeption;

import com.example.intermediate.controller.response.ResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseDto<?> handleException(IllegalArgumentException ex){
        return ResponseDto.fail("BAD_REQUEST",ex.getMessage());
    }
}
