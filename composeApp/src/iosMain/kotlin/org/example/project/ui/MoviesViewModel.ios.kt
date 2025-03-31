package org.example.project.ui

import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

import platform.posix.usleep

//actual fun sleepMillis(millis: Long) {
//    usleep((millis * 1000).toUInt())
//}

actual fun shareMovie(movieTitle: String, movieDescription: String) {
    val items = listOf(movieTitle, movieDescription)
    val activityVC = UIActivityViewController(activityItems = items, applicationActivities = null)

    // Get the top most view controller to present the activity view controller
    val controller = UIApplication.sharedApplication.keyWindow?.rootViewController
    controller?.presentViewController(activityVC, animated = true, completion = null)
}