package com.foro.backend.exceptions;

import org.springframework.validation.BindingResult;

public class ErrorFieldException extends RuntimeException{

    private final transient BindingResult bindingResult;

    public ErrorFieldException(String message, BindingResult bindingResult){
        super(message);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
