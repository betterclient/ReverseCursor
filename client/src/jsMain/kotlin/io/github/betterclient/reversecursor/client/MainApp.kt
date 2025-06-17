package io.github.betterclient.reversecursor.client

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.betterclient.reversecursor.client.util.Icons
import kotlinx.coroutines.delay

@Composable
fun MainApp() {
    val themee = remember { mutableStateOf(darkColorScheme()) }
    var theme by themee
    var darkMode by remember { mutableStateOf(true) }

    MaterialTheme(
        theme,
    ) {
        Box(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize()
                .background(if (darkMode) {
                    Color.DarkGray
                } else {
                    Color.LightGray
                }),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                ColorSchemeSwitcher(darkMode = darkMode) {
                    darkMode = !darkMode
                    themee.value = if (darkMode) darkColorScheme() else lightColorScheme()
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Reverse Cursor",
                    color = theme.onSurface,
                    style = TextStyle(fontSize = 50.sp)
                )

                Spacer(Modifier.height(16.dp))

                val rainbow by animatedRainbowColor()
                Text(
                    text = "Have an idea? We'll ruin it!",
                    color = rainbow,
                    style = TextStyle(fontSize = 16.sp)
                )

                Row {
                    UI(theme)
                }
            }
        }
    }
}

@Composable
fun UI(theme: ColorScheme) {
    Box(
        Modifier
            .size(750.dp, 375.dp)
            .background(
                theme.surface.copy(
                    alpha = 0.8f
                ),
                RoundedCornerShape(16.dp)
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var input by remember { mutableStateOf("") }

            Row {
                TextField(
                    input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .size(500.dp, 100.dp),
                    textStyle = TextStyle(fontSize = 32.sp),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        submit(input)
                    }),
                    placeholder = {
                        AnimateSuggestions()
                    }
                )

                IconButton(onClick = {
                    submit(input)
                }, content = {
                    Icon(
                        Icons.Send,
                        contentDescription = "send"
                    )
                }, modifier = Modifier.size(100.dp))
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateSuggestions() {
    val values = listOf(
        "Build me a file sharing website",
        "Build me a youtube clone",
        "Build me a social media platform",
        "Build me a messaging app"
    )

    var index by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1500)
            index = (index + 1) % values.size
        }
    }

    AnimatedContent(
        targetState = values[index],
        transitionSpec = {
            slideInVertically { height -> height } + fadeIn() togetherWith
                    slideOutVertically { height -> -height } + fadeOut()
        },
        label = "animatedText"
    ) { text ->
        Text(
            text = text,
            style = TextStyle(fontSize = 24.sp, color = Color.Gray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun animatedRainbowColor(): State<Color> {
    val infiniteTransition = rememberInfiniteTransition(label = "rainbow")

    val hue = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "hue"
    )

    return derivedStateOf {
        Color.hsv(hue.value, 1f, 1f)
    }
}

@Composable
fun ColorSchemeSwitcher(
    darkMode: Boolean,
    onToggle: () -> Unit
) {
    val transition = updateTransition(targetState = darkMode, label = "IconTransition")
    val darkModeAlpha by transition.animateFloat(label = "darkModeAlpha") { if (it) 1f else 0f }
    val lightModeAlpha by transition.animateFloat(label = "lightModeAlpha") { if (it) 0f else 1f }

    Box(Modifier.clickable { onToggle() }) {
        Icon(imageVector = Icons.DarkMode, contentDescription = null, modifier = Modifier.alpha(darkModeAlpha).size(100.dp))
        Icon(imageVector = Icons.LightMode, contentDescription = null, modifier = Modifier.alpha(lightModeAlpha).size(100.dp))
    }
}

fun submit(text: String) {
    println(text)
}