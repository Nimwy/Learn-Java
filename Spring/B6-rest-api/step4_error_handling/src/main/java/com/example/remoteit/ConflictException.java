package com.example.remoteit;

// Ném ra khi dữ liệu bị trùng → GlobalExceptionHandler đổi thành 409
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}