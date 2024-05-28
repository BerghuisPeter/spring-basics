package com.example.springbasics.exceptions;

import com.example.springbasics.models.ErrorResponseMessage;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Changes the global return body and status for a given Exception
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<ErrorResponseMessage> handleAuthenticationException(Exception ex) {
        ErrorResponseMessage errorResponseMessage = new ErrorResponseMessage();
        errorResponseMessage.setMessage("Authentication Failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponseMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseMessage> handleBadRequestException(BadRequestException ex) {
        ErrorResponseMessage errorResponseMessage = new ErrorResponseMessage("Invalid Request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseMessage);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponseMessage> handleDuplicateKeyException(DuplicateKeyException ex) {
        ErrorResponseMessage errorResponseMessage = new ErrorResponseMessage();
        errorResponseMessage.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponseMessage);
    }
}