package org.example.project.ui

expect object Logger {
    fun logError(tag: String, message: String, throwable: Throwable?)
}