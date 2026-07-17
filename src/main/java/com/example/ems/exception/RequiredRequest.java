package com.example.ems.exception;

public class RequiredRequest extends RuntimeException {
    public RequiredRequest(String message) {
        super(message);
    }
}
