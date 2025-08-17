package com.wisespendinglife.wise_spending_life.global.controller;

import com.wisespendinglife.wise_spending_life.global.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/s3-presign")
@RequiredArgsConstructor
public class S3UploadController {

    private final S3UploadService s3UploadService;

    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Object>> presign(
            @RequestParam("filename") String filename,
            @RequestParam(value = "contentType", defaultValue = "image/png") String contentType
    ) {
        var res = s3UploadService.presignForPut(filename, contentType, Duration.ofMinutes(5));
        return ResponseEntity.ok(Map.of(
                "putUrl", res.getPutUrl(),
                "key", res.getKey(),
                "objectUrl", res.getObjectUrl()
        ));
    }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Map<String, Object>> presignJson(@RequestBody PresignReq req) {
        var res = s3UploadService.presignForPut(req.filename(), req.contentType() == null ? "image/png" : req.contentType(), Duration.ofMinutes(5));
        return ResponseEntity.ok(Map.of(
                "putUrl", res.getPutUrl(),
                "key", res.getKey(),
                "objectUrl", res.getObjectUrl()
        ));
    }
    private record PresignReq(String filename, String contentType) {}
}
