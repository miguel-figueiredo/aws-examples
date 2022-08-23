package com.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

public class GeneratePresignedURL {

    public static void main(String[] args) throws URISyntaxException {
        Region region = Region.US_EAST_1;
        String bucketName = "test-bucket";
        String objectKey = "issue";

        GetObjectRequest request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(60))
            .getObjectRequest(request)
            .build();

        S3Presigner presigner = S3Presigner.builder()
            .endpointOverride(new URI("http://localhost:4566"))
            .region(region)
            .credentialsProvider(() -> AwsBasicCredentials.create("test-key", "test-secret"))
            .build();

        PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);

        System.out.println(presignedRequest.url());
    }
}