package com.example.remoteit.dto;

public record UploadedFile(
        String key,
        String originalName,
        String contentType,
        long size
) {
}
