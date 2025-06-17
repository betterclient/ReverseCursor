package io.github.betterclient.reversecursor.client.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object Icons {
    val LightMode: ImageVector
        get() {
            if (_LightMode != null) return _LightMode!!

            _LightMode = ImageVector.Builder(
                name = "LightMode",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(480f, 600f)
                    quadToRelative(50f, 0f, 85f, -35f)
                    reflectiveQuadToRelative(35f, -85f)
                    reflectiveQuadToRelative(-35f, -85f)
                    reflectiveQuadToRelative(-85f, -35f)
                    reflectiveQuadToRelative(-85f, 35f)
                    reflectiveQuadToRelative(-35f, 85f)
                    reflectiveQuadToRelative(35f, 85f)
                    reflectiveQuadToRelative(85f, 35f)
                    moveToRelative(0f, 80f)
                    quadToRelative(-83f, 0f, -141.5f, -58.5f)
                    reflectiveQuadTo(280f, 480f)
                    reflectiveQuadToRelative(58.5f, -141.5f)
                    reflectiveQuadTo(480f, 280f)
                    reflectiveQuadToRelative(141.5f, 58.5f)
                    reflectiveQuadTo(680f, 480f)
                    reflectiveQuadToRelative(-58.5f, 141.5f)
                    reflectiveQuadTo(480f, 680f)
                    moveTo(200f, 520f)
                    horizontalLineTo(40f)
                    verticalLineToRelative(-80f)
                    horizontalLineToRelative(160f)
                    close()
                    moveToRelative(720f, 0f)
                    horizontalLineTo(760f)
                    verticalLineToRelative(-80f)
                    horizontalLineToRelative(160f)
                    close()
                    moveTo(440f, 200f)
                    verticalLineToRelative(-160f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(160f)
                    close()
                    moveToRelative(0f, 720f)
                    verticalLineToRelative(-160f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(160f)
                    close()
                    moveTo(256f, 310f)
                    lineToRelative(-101f, -97f)
                    lineToRelative(57f, -59f)
                    lineToRelative(96f, 100f)
                    close()
                    moveToRelative(492f, 496f)
                    lineToRelative(-97f, -101f)
                    lineToRelative(53f, -55f)
                    lineToRelative(101f, 97f)
                    close()
                    moveToRelative(-98f, -550f)
                    lineToRelative(97f, -101f)
                    lineToRelative(59f, 57f)
                    lineToRelative(-100f, 96f)
                    close()
                    moveTo(154f, 748f)
                    lineToRelative(101f, -97f)
                    lineToRelative(55f, 53f)
                    lineToRelative(-97f, 101f)
                    close()
                    moveToRelative(326f, -268f)
                }
            }.build()

            return _LightMode!!
        }

    private var _LightMode: ImageVector? = null

    val DarkMode: ImageVector
        get() {
            if (_DarkMode != null) return _DarkMode!!

            _DarkMode = ImageVector.Builder(
                name = "DarkMode",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(480f, 840f)
                    quadToRelative(-150f, 0f, -255f, -105f)
                    reflectiveQuadTo(120f, 480f)
                    reflectiveQuadToRelative(105f, -255f)
                    reflectiveQuadToRelative(255f, -105f)
                    quadToRelative(14f, 0f, 27.5f, 1f)
                    reflectiveQuadToRelative(26.5f, 3f)
                    quadToRelative(-41f, 29f, -65.5f, 75.5f)
                    reflectiveQuadTo(444f, 300f)
                    quadToRelative(0f, 90f, 63f, 153f)
                    reflectiveQuadToRelative(153f, 63f)
                    quadToRelative(55f, 0f, 101f, -24.5f)
                    reflectiveQuadToRelative(75f, -65.5f)
                    quadToRelative(2f, 13f, 3f, 26.5f)
                    reflectiveQuadToRelative(1f, 27.5f)
                    quadToRelative(0f, 150f, -105f, 255f)
                    reflectiveQuadTo(480f, 840f)
                    moveToRelative(0f, -80f)
                    quadToRelative(88f, 0f, 158f, -48.5f)
                    reflectiveQuadTo(740f, 585f)
                    quadToRelative(-20f, 5f, -40f, 8f)
                    reflectiveQuadToRelative(-40f, 3f)
                    quadToRelative(-123f, 0f, -209.5f, -86.5f)
                    reflectiveQuadTo(364f, 300f)
                    quadToRelative(0f, -20f, 3f, -40f)
                    reflectiveQuadToRelative(8f, -40f)
                    quadToRelative(-78f, 32f, -126.5f, 102f)
                    reflectiveQuadTo(200f, 480f)
                    quadToRelative(0f, 116f, 82f, 198f)
                    reflectiveQuadToRelative(198f, 82f)
                    moveToRelative(-10f, -270f)
                }
            }.build()

            return _DarkMode!!
        }

    private var _DarkMode: ImageVector? = null

    val Send: ImageVector
        get() {
            if (_Send != null) return _Send!!

            _Send = ImageVector.Builder(
                name = "Send",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(120f, 800f)
                    verticalLineToRelative(-640f)
                    lineToRelative(760f, 320f)
                    close()
                    moveToRelative(80f, -120f)
                    lineToRelative(474f, -200f)
                    lineToRelative(-474f, -200f)
                    verticalLineToRelative(140f)
                    lineToRelative(240f, 60f)
                    lineToRelative(-240f, 60f)
                    close()
                    moveToRelative(0f, 0f)
                    verticalLineToRelative(-400f)
                    close()
                }
            }.build()

            return _Send!!
        }

    private var _Send: ImageVector? = null
}