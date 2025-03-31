package org.example.project.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)
val RedError = Color(0xFFB00020)  // Standard Material error color

val DarkColorScheme = Colors(
    background = Black,//Color(0xFF212121),    // #212121 (33,33,33)
    onSurface = Color(0xFFFFFFFF),     // #FFFFFF (255,255,255)
    onBackground = Color(0xFFB3B3B3),    // #B3B3B3 (179,179,179)
    primary = Color(0xFF121212),       // #121212 (18,18,18)
    surface = Color(0xFF212121),//Color(0xFF535353),       // #535353 (83,83,83)
    secondary = White,
    isLight = true,
    primaryVariant = Color(0xFF4D4D4D),
    secondaryVariant = White,
    error = White,
    onPrimary = White,
    onSecondary = White,
    onError = White
)

val LightColorScheme = Colors(
    background = Black,//Color(0xFF212121),    // #212121 (33,33,33)
    onSurface = Color(0xFFFFFFFF),     // #FFFFFF (255,255,255)
    onBackground = Color(0xFFB3B3B3),    // #B3B3B3 (179,179,179)
    primary = Color(0xFF121212),       // #121212 (18,18,18)
    surface = Color(0xFF212121),//Color(0xFF535353),       // #535353 (83,83,83)
    secondary = White,
    isLight = true,
    primaryVariant = Color(0xFF4D4D4D), //between onBackground and surface
    secondaryVariant = White,
    error = White,
    onPrimary = White,
    onSecondary = White,
    onError = White
)