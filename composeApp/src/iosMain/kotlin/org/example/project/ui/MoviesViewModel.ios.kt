package org.example.project.ui

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual fun shareMovie(movieTitle: String, movieDescription: String) {
    val items = listOf(movieTitle, movieDescription)
    val activityVC = UIActivityViewController(activityItems = items, applicationActivities = null)

    // Get the top most view controller to present the activity view controller
    val controller = UIApplication.sharedApplication.keyWindow?.rootViewController
    controller?.presentViewController(activityVC, animated = true, completion = null)
}