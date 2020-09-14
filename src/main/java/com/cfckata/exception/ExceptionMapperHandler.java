package com.cfckata.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionMapperHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        logger.error("ResponseException exception: {}", exception);
        return new ResponseEntity<>(
                new ErrorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleSizeExceededException(HttpServletRequest request, EntityNotFoundException exception) {
        logger.error("EntityNotFoundException exception: {}", exception);

        return new ResponseEntity<>(
                new ErrorResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException exception) {
        logger.error("IllegalArgumentException exception: {}", exception);

        return new ResponseEntity<>(
                new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<ErrorResponse> handleBusinessException(HttpServletRequest request, BusinessException exception) {
        logger.error("Business exception: {}", exception);

        return new ResponseEntity<>(
                new ErrorResponse(exception.getErrorCode(), exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
