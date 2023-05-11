package com.foro.backend;

import com.foro.backend.exceptions.ErrorFieldException;
import com.foro.backend.exceptions.ErrorMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

//    @ExceptionHandler(Throwable.class)
//    @ResponseStatus(INTERNAL_SERVER_ERROR)
//    public ResponseEntity<ErrorMessageResponse> handleUncaughtException(Throwable t) {
//        return buildErrorResponse(t, t.getMessage(), INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler({ObjectNotFoundException.class})
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ErrorMessageResponse> handleObjectNotFoundException(final RuntimeException t) {
        return buildErrorResponse(t, t.getMessage(), NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(PRECONDITION_FAILED)
    public ResponseEntity<ErrorMessageResponse> handleIllegalArgumentException(final IllegalArgumentException t) {
        return buildErrorResponse(t, t.getMessage(), PRECONDITION_FAILED);
    }

//    @ExceptionHandler({AuthorizationServiceException.class, UsernameNotFoundException.class})
//    @ResponseStatus(UNAUTHORIZED)
//    public ResponseEntity<ErrorMessageResponse> handleAuthorizationException(final RuntimeException t) {
//        return buildErrorResponse(t, t.getMessage(), UNAUTHORIZED);
//    }

    @ExceptionHandler(ErrorFieldException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ErrorMessageResponse> handlerException(final ErrorFieldException ex){
        var errors= ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map( fieldError -> fieldError.getDefaultMessage() + fieldError.getField())
                .toList();
        return buildErrorResponse(ex, String.join(", ",errors), BAD_REQUEST);
    }

    private ResponseEntity<ErrorMessageResponse> buildErrorResponse(
            Throwable e, String message, HttpStatus httpStatus) {
        log.error(Arrays.toString(e.getStackTrace()));
        return ResponseEntity.status(httpStatus).body(new ErrorMessageResponse(message, httpStatus.value()));
    }
}
