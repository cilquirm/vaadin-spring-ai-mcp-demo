package us.aadacio.demo

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.mcp.client.McpClient
import org.springframework.ai.mcp.client.McpSyncClient
import org.springframework.ai.mcp.client.transport.HttpClientSseClientTransport
import org.springframework.ai.mcp.spring.McpFunctionCallback
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature
import java.net.http.HttpClient
import io.github.oshai.kotlinlogging.KotlinLogging

@Configuration
class AppConfig {

  val logger = KotlinLogging.logger {} 

    @Bean("hotelSearch")
    fun hotelSearchMCPClient() : McpSyncClient {
      val mapper = ObjectMapper()
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val client = McpClient.using(HttpClientSseClientTransport(HttpClient.newBuilder(), "http://localhost:9090", mapper)).sync()
        client.initialize()
        return client
    }

    @Bean
    fun chatClient(builder: ChatClient.Builder, @Qualifier("hotelSearch") mcpSyncClient: McpSyncClient): ChatClient {
        return builder.defaultFunctions(
            *mcpSyncClient.listTools().tools.map {
                t -> t.let {
                  logger.info{ it } 
                  McpFunctionCallback(mcpSyncClient, t)
                }
            }.toTypedArray()).build()
    }

}
