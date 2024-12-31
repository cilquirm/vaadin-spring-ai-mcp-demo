package us.aadacio.demo

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.template.Id
import com.vaadin.flow.spring.annotation.UIScope
import org.vaadin.spinkit.Spinner
import org.vaadin.spinkit.SpinnerType
import org.vaadin.spinkit.SpinnerSize
import org.springframework.stereotype.Component

@UIScope
@Component
class ChattingWithAISpinner : Div() {

  private val spinner = Spinner(SpinnerType.WAVE).withSize(SpinnerSize.SM).apply {
    isVisible = false
  }
  init {
    style.set("margin-left", "auto")
    style.set("padding", "15px")
    add(spinner)
  }

  fun show() {
    spinner.isVisible = true  
  }

  fun hide() {
    spinner.isVisible = false
  }
  
}
