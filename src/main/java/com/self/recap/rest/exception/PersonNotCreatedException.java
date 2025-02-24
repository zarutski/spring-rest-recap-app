package com.self.recap.rest.exception;

public class PersonNotCreatedException extends RuntimeException{

    public PersonNotCreatedException(String message) {
        super(message);
    }
}
