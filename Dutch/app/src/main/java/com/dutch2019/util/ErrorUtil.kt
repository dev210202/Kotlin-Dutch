package com.dutch2019.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import com.dutch2019.ui.error.ErrorActivity
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.Unit.toString

object IntentValue {
    const val LAST_INTENT = "LAST_INTENT"
    const val ERROR_TEXT = "EXTRA_ERROR_TEXT"
}

fun getMessageByErrorTypeClassify(errorMessage: String?): String {
    when (errorMessage) {
        "Forbidden" -> return "잘못된 앱키를 사용중입니다."
        "Too Many Requests" -> return "호출 건수 제한을 초과했습니다."
    }
    return "TMap API 서버 오류 발생"
}

class ExceptionHandler(application: Application) : Thread.UncaughtExceptionHandler {

    var lastActivity: Activity? = null
    private var activityCount = 0

    init {
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

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

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }
        })
    }

    private fun isSkipActivity(activity: Activity) = activity is ErrorActivity

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        lastActivity?.run {
            startErrorActivity(this, throwable.cause!!.stackTraceToString())
        }
        Process.killProcess(Process.myPid())
        System.exit(0)
    }

    private fun startErrorActivity(activity: Activity, errorText: String) = activity.run {
        val lastActivityIntent = Intent(this, activity.javaClass)
        val errorActivityIntent = Intent(this, ErrorActivity::class.java).apply {
            putExtra(IntentValue.ERROR_TEXT, errorText)
            putExtra(IntentValue.LAST_INTENT, lastActivityIntent)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(errorActivityIntent)
        finish()
    }

}
