package com.example.JobApp;

public class UserException extends RuntimeException{

    private String message;

    UserException(String message){
        super(message);
    }
}
