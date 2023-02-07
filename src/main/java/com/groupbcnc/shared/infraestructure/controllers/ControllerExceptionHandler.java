package com.groupbcnc.shared.infraestructure.controllers;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.groupbcnc.pvp.domain.exceptions.ProductNotFoundException;
import com.groupbcnc.shared.domain.exceptions.ViolatedRuleException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalCurrencyException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Invalid Currency format");

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ViolatedRuleException.class)
    public ResponseEntity<ErrorMessage> violatedRuleException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(ex.getMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
