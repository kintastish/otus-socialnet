package ru.otus.nyuriv.socialnet.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseHttpException extends RuntimeException {
    private int errorCode;

    public BaseHttpException(String message) {
        super(message);
        errorCode = -1;
    }

    // TODO код ошибки
    public BaseHttpException(String message, int errorCode) {
        this(message);
        this.errorCode = errorCode;
    }

    public abstract HttpStatus getStatus();

    public int getErrorCode() {
        return errorCode > 0 ? errorCode : getStatus().value();
    }
}
