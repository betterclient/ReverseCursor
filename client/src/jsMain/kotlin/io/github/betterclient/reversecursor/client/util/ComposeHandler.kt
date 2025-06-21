package io.github.betterclient.reversecursor.client.util

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import io.github.betterclient.reversecursor.client.MainApp

object ComposeHandler {
    private val __content: @Composable () -> Unit = { MainApp()  }

    private val _content = mutableStateOf<@Composable () -> Unit>(value = {
        __content()
    })

    var content: @Composable () -> Unit
        get() {
            return {
                val content = _content.value
                Box {
                    _content.value()
                }
            }
        }
        set(value) {
            _content.value = value
        }
}