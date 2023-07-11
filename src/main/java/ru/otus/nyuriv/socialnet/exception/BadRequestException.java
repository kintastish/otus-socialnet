package ru.otus.nyuriv.socialnet.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseHttpException {
    public BadRequestException(String s) {
        super(s);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
