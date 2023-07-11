package ru.otus.nyuriv.socialnet.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseHttpException {
    public UnauthorizedException(String s) {
        super(s);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
