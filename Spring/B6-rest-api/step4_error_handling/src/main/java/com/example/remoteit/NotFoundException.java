package com.example.remoteit;

// Ném ra khi không tìm thấy dữ liệu → GlobalExceptionHandler đổi thành 404
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}