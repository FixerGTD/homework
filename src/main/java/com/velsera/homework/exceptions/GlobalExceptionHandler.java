package com.velsera.homework.exceptions;

import com.velsera.homework.domain.records.ErrorRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorRecord> handleMissingHeaderException(MissingRequestHeaderException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRecord> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorRecord> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(TweetExceptionBadRequest.class)
    public ResponseEntity<ErrorRecord> handleTweetExceptionBadRequest(TweetExceptionBadRequest e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(TweetExceptionNotFound.class)
    public ResponseEntity<ErrorRecord> handleTweetExceptionNotFound(TweetExceptionNotFound e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(TweetExceptionForbidden.class)
    public ResponseEntity<ErrorRecord> handleTweetExceptionForbidden(TweetExceptionForbidden e) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(TweetException.class)
    public ResponseEntity<ErrorRecord> handleTweetException(TweetException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRecord> handleException(Exception e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorRecord(httpStatus.value(), 103, e.getMessage()), httpStatus);
    }
}
