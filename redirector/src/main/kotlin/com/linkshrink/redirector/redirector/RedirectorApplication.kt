package com.linkshrink.redirector.redirector

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RedirectorApplication

fun main(args: Array<String>) {
	runApplication<RedirectorApplication>(*args)
}
