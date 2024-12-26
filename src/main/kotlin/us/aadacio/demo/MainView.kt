package us.aadacio.demo

import com.github.mvysny.karibudsl.v10.KComposite
import com.github.mvysny.karibudsl.v10.messageInput
import com.github.mvysny.karibudsl.v10.messageList
import com.github.mvysny.karibudsl.v10.verticalLayout
import com.vaadin.flow.component.messages.MessageInput
import com.vaadin.flow.component.messages.MessageList
import com.vaadin.flow.component.messages.MessageListItem
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.ai.chat.model.ChatModel
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant

@Route("")
class MainView @Autowired constructor(val chatModel: ChatModel) : VerticalLayout() {
  val logger = KotlinLogging.logger { }
  private lateinit var chatList: MessageList
  private lateinit var chatInput: MessageInput

  init {
    alignItems = FlexComponent.Alignment.CENTER
    chatList = MessageList(listOf())
    chatList.height = "70vh"

    chatInput = MessageInput({ se ->
      chatList.setItems( chatList.items + MessageListItem(se.value, Instant.now(), "User"))
    })

    add(chatList, chatInput)
  }
}

