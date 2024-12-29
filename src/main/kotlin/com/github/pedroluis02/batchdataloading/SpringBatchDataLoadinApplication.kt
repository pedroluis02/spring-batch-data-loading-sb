package com.github.pedroluis02.batchdataloading

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.system.exitProcess

@SpringBootApplication
class SpringBatchDataLoadingApplication

fun main(args: Array<String>) {
    //runApplication<SpringBatchDataLoadingApplication>(*args)
    exitProcess(SpringApplication.exit(runApplication<SpringBatchDataLoadingApplication>(*args)))
}
