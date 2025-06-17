package io.github.betterclient.reversecursor.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.betterclient.reversecursor.client.util.IFrameManager
import kotlinx.browser.*

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    document.addEventListener("DOMContentLoaded", {
        try {
            IFrameManager.generateIFrameContainer()
            ComposeViewport(document.body!!) {
                MainApp()
            }

            IFrameManager.moveIFrames()
        } catch (e: dynamic) {
            //This is hell.
            window.location.reload()
        }
    })
}