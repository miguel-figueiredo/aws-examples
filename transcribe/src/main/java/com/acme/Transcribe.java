package com.acme;

import java.util.UUID;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.transcribe.TranscribeClient;
import software.amazon.awssdk.services.transcribe.model.LanguageCode;
import software.amazon.awssdk.services.transcribe.model.Media;
import software.amazon.awssdk.services.transcribe.model.MediaFormat;
import software.amazon.awssdk.services.transcribe.model.StartTranscriptionJobRequest;
import software.amazon.awssdk.services.transcribe.model.StartTranscriptionJobResponse;

public class Transcribe {

    public static final String BUCKET = "my-bucket";
    public static final String TRANSCRIPTION_OUTPUT_KEY = "test-transcription.json";
    public static final String MEDIA_FILE_URI = "s3://" + BUCKET + "/sample-0.mp3";
    public static final String ACCESS_KEY_ID = "my-key-id";
    public static final String SECRET_ACCESS_KEY = "my-secret-access-key";

    public static void main(String[] args) {
        TranscribeClient client = TranscribeClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        ACCESS_KEY_ID,
                        SECRET_ACCESS_KEY)))
            .build();

        StartTranscriptionJobRequest jobRequest = StartTranscriptionJobRequest.builder()
            .transcriptionJobName(UUID.randomUUID().toString())
            .mediaFormat(MediaFormat.MP3)
            .languageCode(LanguageCode.EN_US)
            .media(Media.builder()
                .mediaFileUri(MEDIA_FILE_URI)
                .build())
            .outputBucketName(BUCKET)
            .outputKey(TRANSCRIPTION_OUTPUT_KEY).build();

        StartTranscriptionJobResponse jobResponse = client.startTranscriptionJob(jobRequest);

        System.err.println(jobResponse);
    }
}