package io.github.betterclient.reversecursor.client

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    MaterialTheme(darkColorScheme()) {
        Box(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Column {
                IFrame(
                    src = "https://wikipedia.com",
                    id = "yt1",
                    visible = true,
                    modifier = Modifier.width(300.dp).height(300.dp)
                )
                Text(
                    text = "gurt: yo",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}