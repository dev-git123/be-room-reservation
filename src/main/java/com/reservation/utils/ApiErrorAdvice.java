package com.reservation.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiErrorAdvice {

    private HttpServletRequest request;

    @Autowired
    public ApiErrorAdvice(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleEntityNotFoundViolation(EntityNotFoundException e) {

        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("message", e.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        return errorAttributes;
    }



}
