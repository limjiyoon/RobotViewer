package jyl.robotloader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RobotLoaderApplication

fun main(args: Array<String>) {
    runApplication<RobotLoaderApplication>(*args)
}
