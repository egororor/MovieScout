package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIScreen
import platform.UIKit.UIViewController
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        val windowSize = calculateWindowSizeDpIOS()
        App(windowSize = windowSize)
    }
}

// Function to calculate window width in dp as an Int
@OptIn(ExperimentalForeignApi::class)
@Composable
fun calculateWindowSizeDpIOS(): Int {
    val screenWidthPx = UIScreen.mainScreen.bounds.useContents { size.width }
    val density = LocalDensity.current.density

    // Convert px to dp and return as Int
    return (screenWidthPx / density).toInt()
}
