package cz.mprevorovsky.cunici

import io.kvision.*
import io.kvision.core.*
import io.kvision.form.check.CheckStyle
import io.kvision.form.check.checkBox
import io.kvision.html.*
import io.kvision.panel.*
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.px
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

val AppScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

class App : Application() {

    override fun start(state: Map<String, Any>) {
        val root = root("kvapp") {
            vPanel {
                width = 100.perc
                tabPanel {
                    width = 80.perc
                    margin = 20.px
                    marginLeft = auto
                    marginRight = auto
                    padding = 20.px
                    overflow = Overflow.HIDDEN
                    border = Border(2.px, BorderStyle.SOLID, Color.name(Col.SILVER))
                    tab("Čuníci", "fas fa-bars", route = "/cunici") {
                        add(GameTab())
                    }
                    tab("Nastavení", "fas fa-edit", route = "/settings") {
                        add(SettingsTab())
                    }
                }
            }
        }

        AppScope.launch {
            val pingResult = Model.ping("Hello world from client!")
            root.add(Span(pingResult))
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        CoreModule
    )
}

class GameTab : SimplePanel() {
    init {
        this.marginTop = 10.px
        this.minHeight = 400.px
        vPanel(spacing = 3, useWrappers = true) {
            span {
                content = "A simple label"}
            span {
                fontFamily = "Times New Roman"
                fontSize = 32.px
                fontStyle = FontStyle.OBLIQUE
                fontWeight = FontWeight.BOLDER
                fontVariant = FontVariant.SMALLCAPS
                textDecoration =
                    TextDecoration(TextDecorationLine.UNDERLINE,
                        TextDecorationStyle.DOTTED,
                        Color.name(Col.RED))
                content = "A label with custom CSS styling"
            }
            span {
                content = "A list:"
            }
            listTag(ListType.UL,
                listOf("First list element", "Second list element", "Third list element"))
        }
    }
}

class SettingsTab : SimplePanel() {
    init {
        this.marginTop = 10.px
        this.minHeight = 400.px
        vPanel(spacing = 3, useWrappers = true) {
            span {
                content = "Zvolte typy příkladů:"
            }
            val checkboxes = mutableListOf(
            checkBox(true, label = "Sčítání do 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Odčítání do 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Sčítání do 20 bez přechodu přes 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Odčítání do 20 bez přechodu přes 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Sčítání do 20 s přechodem přes 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Odčítání do 20 s přechodem přes 10") { style = CheckStyle.PRIMARY },
            )
            button("vybrat vše").onClick { checkboxes.forEach { it.value = true } }
            button("zrušit vše").onClick { checkboxes.forEach { it.value = false } }
        }
    }
}