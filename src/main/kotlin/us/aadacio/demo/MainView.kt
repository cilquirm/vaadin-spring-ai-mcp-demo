package us.aadacio.demo

import com.github.mvysny.karibudsl.v10.messageInput
import com.github.mvysny.karibudsl.v10.messageList
import com.vaadin.flow.component.DetachEvent
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.messages.MessageInput
import com.vaadin.flow.component.messages.MessageList
import com.vaadin.flow.component.messages.MessageListItem
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.Instant
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.*
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.client.ChatClient
import org.springframework.core.task.TaskExecutor

@Route("")
class MainView constructor(val chatModel: ChatClient, val taskExecutor: TaskExecutor, val spinner: ChattingWithAISpinner) :
        VerticalLayout(), CoroutineScope {
    val logger = KotlinLogging.logger {}
    private var chatList: MessageList
    private var chatInput: MessageInput

    private val uiCoroutineScope = SupervisorJob()
    private val uiCoroutineContext = vaadin()

    init {
        alignItems = FlexComponent.Alignment.CENTER

        chatList = messageList(persistentListOf()) { height = "70vh" }
        chatInput =
                messageInput() {
                    addSubmitListener { se ->
                        logger.info { "adding user response to chat list" }
                        chatList.setItems(
                                chatList.items + MessageListItem(se.value, Instant.now(), "User")
                        )

                        val ui = UI.getCurrent()
                        /*
                        ui.children.findFirst().ifPresent { comp ->
                            comp.children.forEach { a ->
                                logger.info { "${a} : ${a.id.orElse("no id")}" }
                            }
                        }*/

                        spinner.show()

                        logger.info { "calling task executor" }
                        taskExecutor.execute {
                            logger.info { "calling chat model with ${se.value}" }
                            val response = chatModel.prompt(se.value).call().content()
                            logger.info { "received chat model call: $response" }

                            ui.access {
                                logger.info { "updating ui" }
                                chatList.setItems(
                                        chatList.items +
                                                MessageListItem(response, Instant.now(), "AI")
                                )
                                spinner.hide()
                            }
                        }
                    }
                }

        add(chatList, chatInput)
    }

    override val coroutineContext: CoroutineContext
        get() = uiCoroutineContext + uiCoroutineScope

    override fun onDetach(detachEvent: DetachEvent) {
        uiCoroutineScope.cancel()
        logger.info { "Canceled all coroutines started from the UI" }
        super.onDetach(detachEvent)
    }
}
