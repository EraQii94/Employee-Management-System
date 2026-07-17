package com.example.ems.exception;

public class ThereAreEmployeeAssigned extends RuntimeException {
    public ThereAreEmployeeAssigned(String message) {
        super(message);
    }
}
