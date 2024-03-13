package io.exadot.exadotdatafaker.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler
    private ResponseEntity<BadRequestAlertException> handleBadRequestAlertException(BadRequestAlertException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex);
    }

    @ExceptionHandler
    private ResponseEntity<InvalidFakerResources> handleInvalidFakeResources(InvalidFakerResources ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex);
    }

    @ExceptionHandler
    private ResponseEntity<ResourceNotFoundException> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex);
    }
}
