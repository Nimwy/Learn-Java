package com.example.remoteit;

import java.util.Map;

// Mọi lỗi của API đều trả về CÙNG một dạng JSON, frontend dễ xử lý:
// {"code": "...", "message": "...", "errors": {"slug": "..."}}
public record ErrorResponse(String code, String message, Map<String, String> errors) {
}