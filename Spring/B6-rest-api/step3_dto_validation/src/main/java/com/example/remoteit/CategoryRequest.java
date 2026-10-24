package com.example.remoteit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO cho dữ liệu GỬI LÊN: chỉ có các field client được phép gửi (không có id).
// record = class chỉ chứa dữ liệu, Java tự sinh constructor, getter, toString.
public record CategoryRequest(

        @NotBlank(message = "Tên không được để trống")
        @Size(max = 100, message = "Tên tối đa 100 ký tự")
        String name,

        @NotBlank(message = "Slug không được để trống")
        @Pattern(regexp = "[a-z0-9-]+", message = "Slug chỉ gồm chữ thường, số và dấu gạch ngang")
        String slug) {
}