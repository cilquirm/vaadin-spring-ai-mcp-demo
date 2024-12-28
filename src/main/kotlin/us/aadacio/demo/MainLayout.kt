package us.aadacio.demo

import com.github.mvysny.karibudsl.v10.h3
import com.github.mvysny.karibudsl.v10.navbar
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.router.Layout
import com.vaadin.flow.router.RouterLayout

@Layout
class MainLayout : AppLayout(), RouterLayout {

    init {
        navbar { h3("Vaadin Spring AI Demo") }
    }
}
