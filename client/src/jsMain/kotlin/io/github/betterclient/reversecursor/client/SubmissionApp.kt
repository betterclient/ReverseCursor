package io.github.betterclient.reversecursor.client

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import io.github.betterclient.reversecursor.client.util.IFrame
import io.github.betterclient.reversecursor.client.util.IFrameManager
import io.github.betterclient.reversecursor.client.util.Icons
import io.github.betterclient.reversecursor.common.LinGanEncoder
import kotlinx.browser.window
import org.w3c.fetch.RequestInit

var chat = mutableStateListOf<ConversationPiece>()

@Composable
fun Submit() {
    val themee = remember { mutableStateOf(darkColorScheme()) }
    var theme by themee
    val darkMode0 = remember { mutableStateOf(true) }
    var darkMode by darkMode0

    MaterialTheme(theme) {
        Box(
            Modifier.fillMaxSize().background(theme.surface),
            contentAlignment = Alignment.TopCenter
        ) {
            val scrollbar = rememberScrollState()
            Row {
                Chat(scrollbar, theme, Modifier.weight(1f))
                Preview(theme, Modifier.weight(1f))
            }

            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollbar)
            )
        }
    }

    Box(Modifier.fillMaxWidth(0.5f).safeContentPadding(), contentAlignment = Alignment.TopEnd) {
        ColorSchemeSwitcher(darkMode = darkMode) {
            darkMode = !darkMode
            themee.value = if (darkMode) darkColorScheme() else lightColorScheme()
        }
    }
}

@Composable
fun Preview(theme: ColorScheme, modifier: Modifier) {
    IFrame(
        "about:blank",
        modifier.fillMaxHeight(),
        visible = true, "preview-1"
    )
}

@Composable
fun TextFields() {
    var message by remember { mutableStateOf("") }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(16.dp)
                    .height(100.dp),
                textStyle = TextStyle(fontSize = 32.sp),
                placeholder = { Text("Your message here") },
                shape = MaterialTheme.shapes.large
            )

            Button(onClick = {
                if (chat.last().role == "assistant") {
                    chat.add(ConversationPiece("user", message))
                    window.fetch("/message", RequestInit(
                        method = "POST",
                        body = "${LinGanEncoder.encrypt(message)}:${LinGanEncoder.encrypt(chatHash)}"
                    )).then {
                        it.text().then { str ->
                            val message = str.split(":")
                            val out = LinGanEncoder.decrypt(message[0])

                            chat.add(ConversationPiece("assistant", out))
                            IFrameManager.setSRC("preview-1", LinGanEncoder.decrypt(message[1]))
                        }
                    }
                }
            }, content = {
                Icon(
                    Icons.Send,
                    contentDescription = "send",
                    modifier = Modifier.size(75.dp)
                )
            }, modifier = Modifier.size(100.dp))
        }
    }
}

@Composable
fun AIChatBox(scrollbar: ScrollState, theme: ColorScheme) {
    Column(
        Modifier.fillMaxSize().verticalScroll(scrollbar),
    ) {
        Text(
            "Reverse Cursor",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp),
            color = Color.White
        )
        Spacer(Modifier.height(48.dp))

        LaunchedEffect(chat.size) {
            scrollbar.animateScrollTo(scrollbar.maxValue)
        }

        chat.forEach { piece ->
            Row(
                horizontalArrangement = if (piece.role == "user") Arrangement.End else Arrangement.Start,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                Text(
                    piece.message,
                    style = TextStyle(
                        fontSize = 32.sp
                    ),
                    modifier = Modifier
                        .background(theme.primary, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun Chat(scrollbar: ScrollState, theme: ColorScheme, modifier: Modifier) {
    Box(modifier.fillMaxHeight().padding(16.dp)) {
        AIChatBox(scrollbar, theme)
        TextFields()
    }
}

data class ConversationPiece(val role: String, val message: String)
lateinit var chatHash: String