package com.example.JobApp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(UserException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
public ResponseEntity<ApiResponse<String>> UserExceptionHandler(UserException ex){
   ApiResponse<String> res = new ApiResponse<>("error", ex.getMessage(), null);
   return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(AppException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
public ResponseEntity<ApiResponse<String>> AppExceptionHandler(AppException ex){
   ApiResponse<String> res = new ApiResponse<>("error", ex.getMessage(), null);
   return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(RuntimeException.class)
@ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<ApiResponse<String>> RuntimeExceptionHandler(RuntimeException ex){
   ApiResponse<String> res = new ApiResponse<>("Error", ex.getMessage(), null);
   return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
}
}
