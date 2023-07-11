package ru.otus.nyuriv.socialnet.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseHttpException {
    public NotFoundException(String s) {
        super(s);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
