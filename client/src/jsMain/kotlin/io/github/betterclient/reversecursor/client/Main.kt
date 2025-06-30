package io.github.betterclient.reversecursor.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import io.github.betterclient.reversecursor.client.util.ComposeHandler
import io.github.betterclient.reversecursor.client.util.IFrameManager
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    document.addEventListener("DOMContentLoaded", {
        try {
            suspend fun sleepMs(ms: Long) {
                delay(ms)
            }

            CoroutineScope(Dispatchers.Default).launch {
                sleepMs(100)
                IFrameManager.generateIFrameContainer()

                onWasmReady {
                    ComposeViewport(document.body!!) {
                        ComposeHandler.content()
                    }
                }

                IFrameManager.moveIFrames()
            }
        } catch (_: dynamic) {
            //This is hell.
            window.location.reload()
        }
    })
}
