package org.example.service

import io.awspring.cloud.s3.S3PathMatchingResourcePatternResolver
import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client
import java.io.ByteArrayInputStream
import java.io.OutputStream
import java.time.Duration


@Service
class S3Service {

    @Autowired
    private lateinit var s3Client: S3Client

    @Autowired
    private lateinit var s3Template: S3Template

    @Autowired
    private lateinit var resourcePatternResolver: ResourcePatternResolver


    fun getFileS3Path(userName: String, fileName: String): String? {
        return if (s3Template.bucketExists(userName)) {
            s3Template.createSignedGetURL(userName, fileName, Duration.ofMinutes(5)).path
        } else {
            null
        }
    }

    // TODO add versioning
    fun uploadFile(userName: String, fileName: String, fileContent: ByteArray) {
        if (!s3Template.bucketExists(userName)) {
            s3Template.createBucket(userName)
        }
        s3Template.upload(userName, fileName, ByteArrayInputStream(fileContent))
    }

    fun getFileContent(userName: String, fileName: String): OutputStream {
        TODO("Implement me")
    }

    fun deleteFile(userName: String, fileName: String) {
        TODO("Implement me")
    }

    fun listFiles(userName: String): Set<String?> {
        if (!s3Template.bucketExists(userName)) {
            throw IllegalAccessException("User ${userName} doesn't exist")
        }

        val resolver = S3PathMatchingResourcePatternResolver(s3Client, resourcePatternResolver)
        return resolver.getResources("s3://${userName}/*").map { it.filename }.toSet()
    }
}