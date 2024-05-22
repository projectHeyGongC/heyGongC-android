package com.cctv.heygongc

import android.app.Application
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication: Application() {override fun onCreate() {
    super.onCreate()
    initNotificationGroup()
}

    private fun initNotificationGroup() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    }
}