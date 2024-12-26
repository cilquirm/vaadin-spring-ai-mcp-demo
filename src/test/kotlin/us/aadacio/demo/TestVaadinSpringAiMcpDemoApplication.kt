package us.aadacio.demo

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<VaadinSpringAiMcpDemoApplication>().with(TestcontainersConfiguration::class).run(*args)
}
