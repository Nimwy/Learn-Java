package com.example.remoteit;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank @Email(message = "Email không hợp lệ")
        String email,

        @NotBlank @Size(min = 8, message = "Mật khẩu tối thiểu 8 ký tự")
        String password) {
}
