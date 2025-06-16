package io.github.betterclient.reversecursor.client

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*

@Composable
fun App() {
    MaterialTheme(darkColorScheme()) {
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent; println("OUCH! That hurt!") }) {
                Text("Click me!")
            }

            AnimatedVisibility(showContent) {
                Text("gurt jumpscare", color = Color.White)
            }
        }
    }
}