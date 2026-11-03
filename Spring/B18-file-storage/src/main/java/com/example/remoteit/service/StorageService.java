package com.example.remoteit.service;

import com.example.remoteit.config.StorageProperties;
import com.example.remoteit.dto.UploadedFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class StorageService {
    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    private final S3Client s3;
    private final StorageProperties properties;
    private volatile boolean bucketReady;

    public StorageService(S3Client s3, StorageProperties properties) {
        this.s3 = s3;
        this.properties = properties;
    }

    public UploadedFile upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Chọn một file không rỗng");
        }

        ensureBucket();

        String originalName = safeName(file.getOriginalFilename());
        String key = UUID.randomUUID() + "-" + originalName;

        try (InputStream input = file.getInputStream()) {
            s3.putObject(
                    PutObjectRequest.builder()
                            .bucket(properties.bucket())
                            .key(key)
                            .contentType(contentType(file))
                            .contentLength(file.getSize())
                            .build(),
                    RequestBody.fromInputStream(input, file.getSize()));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Không đọc được file", e);
        }

        log.info("Đã upload {} ({} bytes), key={}", originalName, file.getSize(), key);
        return new UploadedFile(key, originalName, contentType(file), file.getSize());
    }

    private synchronized void ensureBucket() {
        if (bucketReady) {
            return;
        }
        try {
            s3.headBucket(HeadBucketRequest.builder().bucket(properties.bucket()).build());
        } catch (S3Exception e) {
            if (e.statusCode() != 404) {
                throw e;
            }
            s3.createBucket(CreateBucketRequest.builder().bucket(properties.bucket()).build());
        }
        bucketReady = true;
    }

    
    private String safeName(String rawName) {
        String name = rawName == null ? "file" : rawName.replace('\\', '/');
        int slash = name.lastIndexOf('/');
        if (slash >= 0) {
            name = name.substring(slash + 1);
        }

        name = name.replaceAll("[^A-Za-z0-9._-]", "_");
        if (name.isBlank() || name.equals(".") || name.equals("..")) {
            return "file";
        }
        return name;
    }

    private String contentType(MultipartFile file) {
        String type = file.getContentType();
        return type == null || type.isBlank() ? "application/octet-stream" : type;
    }
}
