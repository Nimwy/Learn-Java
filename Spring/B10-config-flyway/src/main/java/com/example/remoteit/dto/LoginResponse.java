package com.example.remoteit.dto;

// Body trả về khi đăng nhập: KHÔNG chứa token (token nằm trong cookie)
public record LoginResponse(String email, long expiresIn) {
}
