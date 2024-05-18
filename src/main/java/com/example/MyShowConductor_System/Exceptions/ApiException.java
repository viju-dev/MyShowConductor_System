package com.example.MyShowConductor_System.Exceptions;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);

    }

    public ApiException() { // used in authcontroller login
        super();

    }

}