package com.acme;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

public class Copy {

    public static void main(String[] args) throws URISyntaxException {
        Region region = Region.US_EAST_1;
        String sourceBucket = "test-bucket-source";
        String destinationBucket = "test-bucket-destination";
        String objectKey = "issue";

        CopyObjectRequest request = CopyObjectRequest.builder()
            .sourceBucket(sourceBucket)
            .sourceKey(objectKey)
            .destinationBucket(destinationBucket)
            .destinationKey(objectKey)
            .build();

        S3Client client = S3Client.builder()
            .endpointOverride(new URI("http://localhost:4566"))
            .region(region)
            .credentialsProvider(() -> AwsBasicCredentials.create("test-key", "test-secret"))
            .build();

        client.copyObject(request);
    }
}