package io.github.betterclient.reversecursor.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
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
            console.error("Error during initialization", e)
        }
    })
}

fun generateIFrameContainer() {
    val div = document.createElement("div") as HTMLDivElement
    div.id = "iframe-container"
    document.body!!.appendChild(div)
}