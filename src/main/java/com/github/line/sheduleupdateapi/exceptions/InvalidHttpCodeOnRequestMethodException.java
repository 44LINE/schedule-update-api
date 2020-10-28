package com.github.line.sheduleupdateapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class InvalidHttpCodeOnRequestMethodException extends HttpStatusCodeException {
    public InvalidHttpCodeOnRequestMethodException(HttpStatus statusCode) {
        super(statusCode);
    }
}
