package io.github.betterclient.reversecursor.client

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import io.github.betterclient.reversecursor.client.util.ComposeHandler
import io.github.betterclient.reversecursor.client.util.IFrameManager
import io.github.betterclient.reversecursor.client.util.Icons
import io.github.betterclient.reversecursor.common.LinGanEncoder
import kotlinx.browser.*
import kotlinx.coroutines.*
import org.w3c.dom.HTMLIFrameElement
import org.w3c.fetch.RequestInit
import kotlin.js.json
import kotlin.random.Random

@Composable
fun MainApp() {
    val themee = remember { mutableStateOf(darkColorScheme()) }
    var theme by themee
    val darkMode0 = remember { mutableStateOf(true) }
    var darkMode by darkMode0

    MaterialTheme(
        theme,
    ) {
        BoxWithBackground {
            Box(Modifier.fillMaxSize().safeContentPadding(), contentAlignment = Alignment.TopEnd) {
                ColorSchemeSwitcher(darkMode = darkMode) {
                    darkMode = !darkMode
                    themee.value = if (darkMode) darkColorScheme() else lightColorScheme()
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    Modifier
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
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
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

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
            .shadow(
                elevation = 32.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = theme.surface,
            )
            .background(
                theme.surface,
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

                Button(onClick = {
                    submit(input)
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
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateSuggestions() {
    val values = listOf(
        "Build me a file sharing website",
        "Build me a youtube clone",
        "Build me a social media platform",
        "Build me a messaging app",
        "Build me a music streaming service",
        "Build me a food delivery app",
        "Build me a ride sharing platform",
        "Build me a video conferencing app"
    )

    var index by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1500)
            index = values.filterIndexed { i, _ -> i != index }.random().let { values.indexOf(it) }
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
    val bgColor by animateColorAsState(if(darkMode) Color.LightGray else Color.DarkGray)

    Box(Modifier.clickable { onToggle() }.background(color = bgColor, shape = RoundedCornerShape(16.dp))) {
        Icon(imageVector = Icons.DarkMode, contentDescription = null, modifier = Modifier.alpha(darkModeAlpha).size(100.dp))
        Icon(imageVector = Icons.LightMode, contentDescription = null, modifier = Modifier.alpha(lightModeAlpha).size(100.dp))
    }
}

@Composable
fun BoxWithBackground(
    content: @Composable () -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val density = LocalDensity.current

    suspend fun createBG(): ImageBitmap {
        return withContext(Dispatchers.Default) {
            val bitmap = ImageBitmap(size.width, size.height)
            val brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.LightGray, Color(0xFF666666)
                ),
                startX = 0f,
                endX = size.width.toFloat()
            )

            CanvasDrawScope().draw(
                density = density,
                layoutDirection = LayoutDirection.Ltr,
                size = size.toSize(),
                canvas = Canvas(bitmap)
            ) {
                drawRect(brush = brush, size = size.toSize())
            }

            val random = Random.Default

            val blockSize = 16
            val canvas = Canvas(bitmap)
            val startX = (size.width * 0.75f).toInt().coerceAtMost(size.width)
            for (xBlock in startX until size.width step blockSize) {
                val distortionFactor = ((xBlock - startX).toFloat() / (size.width - startX)).coerceAtMost(1f)

                for (yBlock in 0 until size.height step blockSize) {
                    if (random.nextFloat() < distortionFactor * 0.3f) {
                        val color = if (random.nextBoolean()) Color.Black else Color.White

                        val rect = Rect(
                            left = xBlock.toFloat(),
                            top = yBlock.toFloat(),
                            right = (xBlock + blockSize).coerceAtMost(size.width).toFloat(),
                            bottom = (yBlock + blockSize).coerceAtMost(size.height).toFloat()
                        )

                        val paint = Paint().apply {
                            this.color = color.copy(alpha = 0.1f)
                        }

                        canvas.drawRect(rect, paint)
                    }
                }
            }

            bitmap
        }
    }

    var background by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        while (true) {
            background = createBG()
            delay(500)
        }
    }

    LaunchedEffect(size) {
        background = createBG()
    }

    Box(
        modifier = Modifier
            .onSizeChanged { size = it }
            .safeContentPadding()
            .fillMaxSize()
            .drawBehind {
                drawImage(background!!)
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

fun submit(text: String) {
    if (text.isEmpty()) return

    MainScope().launch {
        val message = LinGanEncoder.encrypt(text)
        chat = mutableStateListOf(ConversationPiece("user", text))

        ComposeHandler.content = { Submit() }
        window.fetch("/generate", object : RequestInit {
            override var method: String? = "POST"
            override var headers = json("Content-Type" to "application/json")
            override var body = message
        }).then {
            it.text().then { str ->
                val split = str.split(":")

                chatHash = LinGanEncoder.decrypt(split[2])
                chat.add(ConversationPiece("assistant", LinGanEncoder.decrypt(split[0])))
                IFrameManager.setSRC("preview-1", LinGanEncoder.decrypt(split[1]))
            }
        }
    }
}