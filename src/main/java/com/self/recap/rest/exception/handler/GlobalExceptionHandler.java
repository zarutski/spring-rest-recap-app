package com.self.recap.rest.exception.handler;

import com.self.recap.rest.exception.PersonNotCreatedException;
import com.self.recap.rest.exception.PersonNotFoundException;
import com.self.recap.rest.exception.response.PersonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handlePersonNotFound(PersonNotFoundException e) {
        return new ResponseEntity<>(new PersonErrorResponse("Person not found", System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<PersonErrorResponse> handlePersonNotCreated(PersonNotCreatedException e) {
        return new ResponseEntity<>(new PersonErrorResponse(e.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}
