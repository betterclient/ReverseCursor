package io.github.betterclient.reversecursor.client

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import kotlinx.browser.document
import org.w3c.dom.HTMLIFrameElement

@Composable
fun IFrame(
    src: String,
    modifier: Modifier = Modifier,
    visible: Boolean, id: String
) {
    Box(modifier.onGloballyPositioned { coords ->
        try {
            position(coords, id, src)
        } catch (e: Exception) {
            console.error("Error positioning iframe with id $id", e)
        }
    })

    AnimatedVisibility(visible) {
        if (visible) {
            //create, it will be positioned later
            val i = document.getElementById(id)?: (document.createElement("iframe").also {
                it.id = id
                (it as HTMLIFrameElement).src = src
            }) as HTMLIFrameElement

            if (document.getElementById(id) == null) {
                document.getElementById("iframe-container")!!.appendChild(i)
            }
        } else {
            //remove
            val frame = document.getElementById(id)
            frame?.remove()
        }
    }
}

fun position(coords: LayoutCoordinates, id: String, src: String) {
    val position = coords.positionOnScreen()
    val size = coords.size

    //probably already created, check anyway.
    val frame = document.getElementById(id)?: (document.createElement("iframe").also {
        it.id = id
        (it as HTMLIFrameElement).src = src
    }) as HTMLIFrameElement

    if (document.getElementById(id) == null) {
        document.getElementById("iframe-container")!!.appendChild(frame)
    }

    //position and scale
    frame.setAttribute("style", """
            position: absolute;
            top: ${position.y}px;
            left: ${position.x}px;
            width: ${size.width}px;
            height: ${size.height}px;
            border: none;
        """.trimIndent().replace("\n", " "))
}