package us.aadacio.demo

import com.vaadin.flow.component.page.AppShellConfigurator
import com.vaadin.flow.component.page.Push
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication @Push class VaadinSpringAiMcpDemoApplication : AppShellConfigurator

fun main(args: Array<String>) {
    runApplication<VaadinSpringAiMcpDemoApplication>(*args)
}
