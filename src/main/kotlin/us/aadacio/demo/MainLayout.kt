package us.aadacio.demo

import com.github.mvysny.karibudsl.v10.div
import com.github.mvysny.karibudsl.v10.h3
import com.github.mvysny.karibudsl.v10.navbar
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.router.Layout
import com.vaadin.flow.router.RouterLayout
import org.vaadin.spinkit.Spinner
import org.vaadin.spinkit.SpinnerSize
import org.vaadin.spinkit.SpinnerType
import org.springframework.beans.factory.annotation.Autowired

@Layout
class MainLayout @Autowired constructor(val spinner: ChattingWithAISpinner) : AppLayout(), RouterLayout {

    init {
        navbar {
            h3("Vaadin Spring AI Demo")
            add(spinner)
        }
    }
}
