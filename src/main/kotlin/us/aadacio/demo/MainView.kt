package us.aadacio.demo

import com.github.mvysny.karibudsl.v10.messageInput
import com.github.mvysny.karibudsl.v10.messageList
import com.vaadin.flow.component.messages.MessageInput
import com.vaadin.flow.component.messages.MessageList
import com.vaadin.flow.component.messages.MessageListItem
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Instant
import org.springframework.ai.chat.model.ChatModel
import org.springframework.beans.factory.annotation.Autowired

@Route("")
class MainView @Autowired constructor(val chatModel: ChatModel) : VerticalLayout() {
    val logger = KotlinLogging.logger {}
    private var chatList: MessageList
    private var chatInput: MessageInput

    init {
        alignItems = FlexComponent.Alignment.CENTER

        chatList = messageList { height = "70vh" }

        chatInput = messageInput {
            addSubmitListener { se ->
                chatList.setItems(chatList.items + MessageListItem(se.value, Instant.now(), "User"))
            }
        }

        add(chatList, chatInput)
    }
}
