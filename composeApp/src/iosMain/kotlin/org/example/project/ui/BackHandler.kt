package org.example.project.ui

import androidx.compose.runtime.Composable

@Composable
actual fun HandleBackPress(onBackPressed: () -> Unit) {
    // iOS does not have a system back button, so no action is needed
}