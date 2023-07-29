package com.blogapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private long id;

    /*public ResourceNotFoundException(long id) {
        this.id = id;
    }*/
    public ResourceNotFoundException(long id) {
        super("Resource Not Found for id: "+id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
