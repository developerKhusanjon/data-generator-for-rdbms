package io.exadot.exadotdatafaker.controller.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidFakerResources extends ReflectiveOperationException {

    public InvalidFakerResources(String message) {
        super(message);
    }
}