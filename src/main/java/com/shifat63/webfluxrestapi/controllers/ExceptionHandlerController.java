package com.shifat63.webfluxrestapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Author: Shifat63

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(Exception exception){
        return exception.toString();
    }
}
