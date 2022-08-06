package com.example.intermediate.exeption;

import lombok.Getter;
import lombok.Setter;

@Getter
public class RestApiException {
    private final String errorMessage;

    private RestApiException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static RestApiException of (String errorMessage){
        return new RestApiException(errorMessage);
    }
}
