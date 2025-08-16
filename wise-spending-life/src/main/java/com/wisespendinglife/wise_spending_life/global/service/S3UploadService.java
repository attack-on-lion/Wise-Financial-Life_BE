package com.wisespendinglife.wise_spending_life.global.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class S3UploadService {

    private static final Set<String> ALLOWED = Set.of(
            "image/png", "image/jpeg", "image/webp"
    );

    private final S3Presigner presigner;

    @Value("${s3.bucket:${S3_BUCKET}}")
    private String bucket;

    @Value("${s3.base-prefix:${S3_BASE_PREFIX:}}")
    private String basePrefix;

    @Value("${s3.region:${aws.region:${AWS_REGION:${AWS_DEFAULT_REGION:}}}}")
    private String region;

    public PresignResponse presignForPut(String originalFilename, String contentType, Duration ttl) {
        if (!ALLOWED.contains(contentType)) {
            throw new IllegalArgumentException("Unsupported contentType: " + contentType);
        }

        // 확장자 안전 추출
        String ext = "";
        int dot = originalFilename.lastIndexOf('.');
        if (dot >= 0 && dot < originalFilename.length() - 1) {
            ext = originalFilename.substring(dot);
        }

        String ts = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "").substring(0,12);

        // 규칙: <prefix>/<원본명_YYYYMMDDHHmmss_UUID>.<ext>
        String baseName = (dot > 0) ? originalFilename.substring(0, dot) : originalFilename;
        String safeBase = baseName.replaceAll("[^a-zA-Z0-9._-]", "_"); // S3 key 안전화
        String finalName = String.format("%s_%s_%s%s", safeBase, ts, uuid, ext);

        String key = (basePrefix == null ? "" : basePrefix) + finalName;

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(ttl) // e.g. 5 min
                .putObjectRequest(objectRequest)
                .build();

        PresignedPutObjectRequest presigned = presigner.presignPutObject(presignRequest);

        return new PresignResponse(
                presigned.url().toString(),
                key,
                toObjectUrl(key)
        );
    }

    public String toObjectUrl(String key) {
        // 퍼블릭 열람이 아니라면 직접 접근은 막힐 수 있음(권장: CloudFront+OAC)
        return "https://" + bucket + ".s3." + region + ".amazonaws.com/" + key;
    }

    @Getter
    @AllArgsConstructor
    public static class PresignResponse {
        private String putUrl;
        private String key;
        private String objectUrl; // 바로 보이진 않아도 저장/표시 용도
    }
}
