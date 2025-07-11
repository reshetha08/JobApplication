package com.example.JobApp;

import org.springframework.web.bind.annotation.RestControllerAdvice;


public class AppException extends RuntimeException{

    private String message;

    AppException(String message){
        super(message);
    }
}
