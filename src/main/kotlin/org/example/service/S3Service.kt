package org.example.service

import io.awspring.cloud.s3.S3Template
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client


@Service
class S3Service {

    @Autowired
    private lateinit var s3Client: S3Client

    @Autowired
    private lateinit var s3Template: S3Template


    fun getFileS3Path(userName: String, fileName: String): String? {
        return if (s3Template.bucketExists(userName)) {
            s3Template.listObjects(userName, "").get(0).filename
        } else {
            null
        }
    }
}