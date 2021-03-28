package com.example.dolaptendyol.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    var rootWarningShown = false
    override fun onCreate() {
        super.onCreate()
        instance = this
        rootWarningShown = false
        appContext = applicationContext
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    companion object {
        var instance: App? = null
            private set
        var appContext: Context? = null
            private set
    }
}