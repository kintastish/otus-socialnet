package ru.otus.nyuriv.socialnet.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.otus.nyuriv.socialnet.exception.BaseHttpException;
import ru.otus.nyuriv.socialnet.model.ErrorResponse;

@RestControllerAdvice
@Slf4j
public class ExceptionMapper {
    private static final String UNKNOWN_ERROR_MSG = "Unknown error occurred";
    @ExceptionHandler(BaseHttpException.class)
    public ResponseEntity<ErrorResponse> handleBaseHttpException(BaseHttpException ex) {
        log.error("Caught exception", ex);
        return makeResponseEntity(ex.getMessage(), ex.getErrorCode(), ex.getStatus());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(Throwable th) {
        log.error("Caught unexpected error", th);
        // TODO код ошибки
        return makeServerErrorResponseEntity(th.getMessage());
    }

    // исключения спринга
    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ErrorResponse> handleRequestBindingException(ServletRequestBindingException ex) {
        ResponseEntity<ErrorResponse> err;
        if (ex instanceof MissingPathVariableException || ex instanceof MissingMatrixVariableException) {
            log.error("Caught exception: {}", ex.getMessage(), ex);
            err = makeServerErrorResponseEntity(ex.getMessage());
        } else {
            String message = getRequestBindingExceptionMessage(ex);
            log.info("Wrong request: {}", message);
            err = makeResponseEntity(message, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        }
        return err;
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(TypeMismatchException ex) {
        ResponseEntity<ErrorResponse> err;
        if (ex instanceof MethodArgumentTypeMismatchException) {
            log.warn("Argument type mismatch, {}", ex.getMessage());
            err = makeResponseEntity("Wrong argument type", HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST);
        } else {
            log.error("Caught exception: {}", ex.getMessage(), ex);
            err = makeServerErrorResponseEntity("Conversion not supported");
        }
        return err;
    }
    private String getRequestBindingExceptionMessage(ServletRequestBindingException ex) {
        if (ex instanceof MissingRequestCookieException) {
            return "Cookie '" + ((MissingRequestCookieException) ex).getCookieName() + "' required";
        }
        if (ex instanceof MissingRequestHeaderException) {
            return "Header '" + ((MissingRequestHeaderException) ex).getHeaderName() + "' required";
        }
        if (ex instanceof MissingServletRequestParameterException) {
            return "Parameter '" + ((MissingServletRequestParameterException) ex).getParameterName() + "' required";
        }
        // сюда должен попадать только UnsatisfiedServletRequestParameterException
        return ex.getMessage();
    }

    private ResponseEntity<ErrorResponse> makeResponseEntity(String msg, int errCode, HttpStatus status) {
        ErrorResponse resp = new ErrorResponse();
        resp.setMessage(msg == null || msg.isEmpty() ? UNKNOWN_ERROR_MSG : msg);
        resp.setCode(errCode);
        // TODO requestId
        resp.setRequestId("todo-request-id");
        return new ResponseEntity<>(resp, status);
    }

    private ResponseEntity<ErrorResponse> makeServerErrorResponseEntity(String msg) {
        return makeResponseEntity(msg, HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
