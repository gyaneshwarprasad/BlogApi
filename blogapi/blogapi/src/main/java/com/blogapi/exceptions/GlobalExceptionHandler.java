package com.blogapi.exceptions;

import com.blogapi.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    //this class is responsible to Suppress exception and handles ResourceNotFoundException & gives the readable msg
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException
            (ResourceNotFoundException exception, WebRequest webRequest){
        //WebRequest is an in-built class which helps to send back information to Postman

        ErrorDetails errorDetails = new ErrorDetails( new Date(), exception.getMessage(),
                                    webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    //this class is responsible to Suppress any exception and gives the readable msg
    public ResponseEntity<ErrorDetails> handleGlobalException (Exception exception, WebRequest webRequest){
        //WebRequest is an in-built class which helps to send back information to Postman

        ErrorDetails errorDetails = new ErrorDetails( new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

}