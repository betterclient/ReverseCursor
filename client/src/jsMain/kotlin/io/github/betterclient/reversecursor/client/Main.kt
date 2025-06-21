package io.github.betterclient.reversecursor.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.betterclient.reversecursor.client.util.ComposeHandler
import io.github.betterclient.reversecursor.client.util.IFrameManager
import kotlinx.browser.*

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    document.addEventListener("DOMContentLoaded", {
        try {
            IFrameManager.generateIFrameContainer()
            ComposeViewport(document.body!!) {
                ComposeHandler.content()
            }

            IFrameManager.moveIFrames()
        } catch (_: dynamic) {
            //This is hell.
            window.location.reload()
        }
    })
}
