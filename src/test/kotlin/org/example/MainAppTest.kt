package org.example

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainAppTest(
    @LocalServerPort private val port: Int
) {

    private val restTemplate: TestRestTemplate = TestRestTemplate(RestTemplateBuilder())

    @Test
    fun contextLoads() {

    }

    @Test
    fun exampleEndpointTest() {
        val response: ResponseEntity<String> = restTemplate.getForEntity("http://localhost:$port/", String::class.java)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

}