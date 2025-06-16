package io.github.betterclient.reversecursor.client

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    document.addEventListener("DOMContentLoaded", {
        ComposeViewport(document.body!!) {
            App()
        }
    })
}