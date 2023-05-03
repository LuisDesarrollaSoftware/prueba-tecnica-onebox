package com.pruebatecnicaonebox.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class BadRequestException  extends OneBoxApplicationException{

    public BadRequestException() {
        super("Bad Request");
    }
    public BadRequestException(String message) {
        super(message);
    }
}
