package com.dutch2019.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import com.dutch2019.ui.error.ErrorActivity
import com.dutch2019.ui.main.MainActivity
import java.io.PrintWriter
import java.io.StringWriter


class ExceptionHandler(
    application: Application,
    private val crashlyticsExceptionHandler: Thread.UncaughtExceptionHandler
) : Thread.UncaughtExceptionHandler {

    var lastActivity: Activity? = null
    private var activityCount = 0

    init {
        application.registerActivityLifecycleCallbacks(object : SimpleActivityLifecycleCallbacks() {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                if (isSkipActivity(activity)) {
                    return
                }
                lastActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {
                if (isSkipActivity(activity)) {
                    return
                }
                activityCount++
                lastActivity = activity
            }

            override fun onActivityStopped(activity: Activity) {
                if (isSkipActivity(activity)) {
                    return
                }
                activityCount--
                if (activityCount < 0) {
                    lastActivity = null
                }

            }
        })
    }

    private fun isSkipActivity(activity: Activity) = activity is ErrorActivity

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        lastActivity?.run {
            val stringWriter = StringWriter()
            throwable.printStackTrace(PrintWriter(stringWriter))
            startErrorActivity(this, stringWriter.toString())
        }
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

    private fun startErrorActivity(activity: Activity, errorText: String) = activity.run {
        val lastActivityIntent = Intent(this, activity.javaClass)
        val errorActivityIntent = Intent(this, ErrorActivity::class.java).apply {
                putExtra("errorText", errorText)
                putExtra("lastIntent", lastActivityIntent)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        startActivity(errorActivityIntent)
        finish()
    }

}


abstract class SimpleActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    override fun onActivityPaused(activity: Activity) {
        // no-op
    }

    override fun onActivityResumed(activity: Activity) {
        // no-op
    }

    override fun onActivityStarted(activity: Activity) {
        // no-op
    }

    override fun onActivityDestroyed(activity: Activity) {
        // no-op
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // no-op
    }

    override fun onActivityStopped(activity: Activity) {
        // no-op
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // no-op
    }
}
