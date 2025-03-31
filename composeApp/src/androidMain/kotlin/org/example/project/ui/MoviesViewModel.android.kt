package org.example.project.ui

import android.app.Activity
import android.content.Intent
import org.example.project.MyApp

//actual fun sleepMillis(millis: Long) {
//    Thread.sleep(millis)
//}

actual fun shareMovie(movieTitle: String, movieDescription: String) {
    // Use the current Activity if available; otherwise fallback to the application instance.
    val context = MyApp.instance.currentActivity ?: MyApp.instance
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, movieTitle)
        putExtra(Intent.EXTRA_TEXT, movieDescription)
    }
    // Only add FLAG_ACTIVITY_NEW_TASK if the context is not an Activity
    if (context !is Activity) {
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    val chooserIntent = Intent.createChooser(shareIntent, "Share Movie via")
    context.startActivity(chooserIntent)
}