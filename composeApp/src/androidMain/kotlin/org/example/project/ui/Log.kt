package org.example.project.ui

import android.util.Log

actual object Logger {
    actual fun logError(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}