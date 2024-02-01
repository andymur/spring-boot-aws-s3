package org.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MySpringBootApplication

fun main(args: Array<String>) {
    SpringApplication.run(MySpringBootApplication::class.java, *args)
}