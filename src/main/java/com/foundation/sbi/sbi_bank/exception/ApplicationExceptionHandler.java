package com.foundation.sbi.sbi_bank.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class, RuntimeException.class, Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ExceptionResponse handleResourceNotFoundException(Exception exception, HttpServletRequest request) {
        ExceptionResponse  ex = new ExceptionResponse();
        ex.setMessage(exception.getMessage());
        ex.setUrl(request.getRequestURI());
        return ex;
    }
}
