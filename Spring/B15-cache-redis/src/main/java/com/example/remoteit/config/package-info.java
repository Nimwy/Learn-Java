/**
 * Cấu hình hạ tầng: {@code SecurityConfig} (filter chain, phân quyền),
 * {@code JwtConfig} (JwtEncoder/JwtDecoder) và {@code CookieTokenResolver}
 * (đọc token từ cookie cho Spring Security). {@code RequestLoggingFilter}
 * ghi log HTTP và gắn requestId cho các dòng log của cùng một request.
 */
package com.example.remoteit.config;
