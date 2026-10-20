package com.example.coindesk.exception;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(CurrencyNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleNotFound(CurrencyNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Collections.singletonMap("message", e.getMessage()));
        }

        @ExceptionHandler(CurrencyConflictException.class)
        public ResponseEntity<Map<String, String>> handleConflict(CurrencyConflictException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(Collections.singletonMap("message", e.getMessage()));
        }

        //驗證錯誤時處理
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, String>> handleInvalid(MethodArgumentNotValidException e) {
                FieldError fieldError = e.getBindingResult().getFieldError();
                String message = fieldError == null
                                ? "參數錯誤"
                                : fieldError.getField() + " " + fieldError.getDefaultMessage();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(Collections.singletonMap("message", message));
        }
}