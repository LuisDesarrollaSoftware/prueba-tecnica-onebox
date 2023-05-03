package com.pruebatecnicaonebox.model.exceptions;

public class OneBoxApplicationException extends Exception{

    public OneBoxApplicationException(String message){
        super(message);
    }

    public OneBoxApplicationException(String message, Throwable cause){
        super(message, cause);
    }

    public OneBoxApplicationException(Throwable cause){
        super(cause);
    }

    public OneBoxApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
