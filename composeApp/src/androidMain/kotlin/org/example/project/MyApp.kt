package org.example.project

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MyApp : Application(), Application.ActivityLifecycleCallbacks {

    companion object {
        lateinit var instance: MyApp
            private set
    }

    var currentActivity: Activity? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this  // Initialize the singleton instance
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (currentActivity === activity) {
            currentActivity = null
        }
    }

    // Other lifecycle callbacks (can be empty)
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}