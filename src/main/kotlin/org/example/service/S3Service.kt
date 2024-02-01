package org.example.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import java.net.URI
import java.nio.file.Path

@Service
class S3Service {

    @Value("\${aws.s3.endpoint}")
    private lateinit var s3Endpoint: String

    @Value("\${aws.region}")
    private lateinit var awsRegion: String

    @Value("\${aws.accessKey}")
    private lateinit var awsAccessKey: String

    @Value("\${aws.secretKey}")
    private lateinit var awsSecretKey: String

    /*private val s3Client: S3Client = S3Client.builder()
        .endpointOverride(URI.create(s3Endpoint))
        .credentialsProvider(
            StaticCredentialsProvider.create(
                AwsBasicCredentials.create(awsAccessKey, awsSecretKey)
            )
        )
        .region(Region.of(awsRegion))
        .build()*/

    fun getFileS3Path(userName: String, fileName: String): String {
        val s3Client: S3Client = S3Client.builder()
            .endpointOverride(URI.create(s3Endpoint))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(awsAccessKey, awsSecretKey)
                )
            )
            .region(Region.of(awsRegion))
            .build()
        return s3Client.getObject(GetObjectRequest.builder().bucket("").build(), Path.of(fileName)).toString()
    }
}