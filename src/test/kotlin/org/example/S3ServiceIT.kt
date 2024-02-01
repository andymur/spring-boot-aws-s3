package org.example

import org.example.service.S3Service
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest(properties = ["aws.s3.endpoint=localhost"])
class S3ServiceIT {

    companion object {

        val LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack")

        val localstack: LocalStackContainer = LocalStackContainer(LOCALSTACK_IMAGE)
            .withServices(LocalStackContainer.Service.S3)
            .also { it.start() }

        @DynamicPropertySource
        @JvmStatic
        fun configureProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.cloud.aws.s3.endpoint") { localstack.endpoint.toString()}
            registry.add("aws.s3.endpoint") { localstack.endpoint.toString() }
            registry.add("aws.region") { localstack.region }
            registry.add("aws.accessKey") { localstack.accessKey }
            registry.add("aws.secretKey") { localstack.secretKey }
        }
    }

    @Autowired
    private lateinit var s3Service: S3Service


    @Test
    fun `test s3`() {
        s3Service.getFileS3Path("john", "file.tmp")
    }
}