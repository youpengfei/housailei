package com.showcase.securitydemo.exception;

public class ServiceUncheckedException extends SystemUncheckedException {
    public ServiceUncheckedException(String message) {
        super(message);
    }

    public ServiceUncheckedException(Throwable cause) {
        super(cause);
    }
}
