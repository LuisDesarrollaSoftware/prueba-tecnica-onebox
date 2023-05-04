package com.pruebatecnicaonebox.filter;

import com.pruebatecnicaonebox.model.exceptions.OneBoxApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = OneBoxApplicationException.class)
    protected ResponseEntity<Object> handleOneBoxApplicationException(OneBoxApplicationException ex, WebRequest request) {
        ResponseStatus annotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        HttpStatus status = annotation != null ? annotation.code() : HttpStatus.BAD_REQUEST;
        String message = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : status.getReasonPhrase();
        logException(status, message, ex);
        return new ResponseEntity<>(message, status);
    }

    private void logException(HttpStatus status, String message, Exception ex) {
        if (status.is4xxClientError()) {
            LOG.warn(message, ex);
        } else if (status.is5xxServerError()) {
            LOG.error(message, ex);
        }
    }
}
