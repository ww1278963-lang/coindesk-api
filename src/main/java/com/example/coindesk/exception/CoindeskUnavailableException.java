package com.example.coindesk.exception;

public class CoindeskUnavailableException extends RuntimeException {

    public CoindeskUnavailableException(String message) {
            super(message);
    }
}
