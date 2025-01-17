package us.aadacio.demo

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.mcp.client.McpClient
import org.springframework.ai.mcp.client.McpSyncClient
import org.springframework.ai.mcp.client.transport.HttpClientSseClientTransport
import org.springframework.ai.mcp.spring.McpFunctionCallback
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {

    @Bean("hotelSearch")
    fun hotelSearchMCPClient() : McpSyncClient {
        return McpClient.using(HttpClientSseClientTransport("http://localhost:9090")).sync()
    }

    @Bean
    fun chatClient(builder: ChatClient.Builder, @Qualifier("hotelSearch") mcpSyncClient: McpSyncClient): ChatClient {
        return builder.defaultFunctions(
            *mcpSyncClient.listTools().tools.map {
                t -> McpFunctionCallback(mcpSyncClient, t)
            }.toTypedArray()).build()
    }

}