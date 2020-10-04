package com.github.line.sheduleupdateapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidHttpCodeOnRequestMethodAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidHttpCodeOnRequestMethodException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    String invalidHttpCodeOnRequestMethodAdvice(InvalidHttpCodeOnRequestMethodException e) {
        return e.getMessage();
    }
}
