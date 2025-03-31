package org.example.project.ui

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun HandleBackPress(onBackPressed: () -> Unit) {
    BackHandler {
        onBackPressed()
    }
}