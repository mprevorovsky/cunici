package cz.mprevorovsky.cunici

import io.kvision.*
import io.kvision.core.*
import io.kvision.form.check.CheckStyle
import io.kvision.form.check.checkBox
import io.kvision.form.formPanel
import io.kvision.form.text.Text
import io.kvision.html.InputType
import io.kvision.html.Span
import io.kvision.html.button
import io.kvision.html.span
import io.kvision.panel.*
import io.kvision.utils.auto
import io.kvision.utils.perc
import io.kvision.utils.pt
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
                    tab("Počítej!", route = "/practice") {
                        add(PracticeTab())
                    }
                    tab("Nastavení", route = "/settings") {
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

class PracticeTab : SimplePanel() {
    init {
        this.marginTop = 20.px
        this.minHeight = 400.px
        hPanel(spacing = 3, useWrappers = true) {
            span {
                content = "12 + 3 = "
                fontSize = 25.pt
                marginRight = 10.px
            }
            val formPanel = formPanel<String> {
                add("Výsledek",
                    Text(type = InputType.TEXT),

                    validatorMessage = { "Zadávejte pouze čísla" }) {
                    it.getValue()?.let { "^\\d+$".toRegex().matches(it) }
                }
            }
        }
    }
}

class SettingsTab : SimplePanel() {
    init {
        this.marginTop = 20.px
        this.minHeight = 400.px
        vPanel(spacing = 10, useWrappers = true) {
            hPanel {
                marginLeft = 10.px
                marginBottom = 10.px
                span {
                    content = "Zvolte typy příkladů:"
                    fontWeight = FontWeight.BOLDER
                }
            }
            val checkboxes = mutableListOf(
            checkBox(true, label = "Sčítání do 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Odčítání do 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Sčítání do 20 bez přechodu přes 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Odčítání do 20 bez přechodu přes 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Sčítání do 20 s přechodem přes 10") { style = CheckStyle.PRIMARY },
            checkBox(true, label = "Odčítání do 20 s přechodem přes 10") { style = CheckStyle.PRIMARY },
            )
            hPanel {
                spacing = 10
                marginTop = 10.px
                button("Vybrat vše").onClick { checkboxes.forEach { it.value = true } }
                button("Zrušit vše").onClick { checkboxes.forEach { it.value = false } }
            }
        }
    }
}