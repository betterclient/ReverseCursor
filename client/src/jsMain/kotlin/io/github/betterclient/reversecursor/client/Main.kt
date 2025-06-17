package io.github.betterclient.reversecursor.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.*
import org.w3c.dom.HTMLDivElement

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    document.addEventListener("DOMContentLoaded", {
        try {
            generateIFrameContainer()
            ComposeViewport(document.body!!) {
                App()
            }

            val iframeContainer = document.getElementById("iframe-container") as HTMLDivElement
            document.body!!.appendChild(iframeContainer)
        } catch (e: Exception) {
            if (e.stackTraceToString().contains("org_jetbrains_skiko")) {
                window.location.reload()
            }

            e.printStackTrace()
        }
    })
}

fun generateIFrameContainer() {
    val div = document.createElement("div") as HTMLDivElement
    div.id = "iframe-container"
    document.body!!.appendChild(div)
}