package com.lytics.android.demo

import android.app.Application
import com.lytics.android.Lytics
import com.lytics.android.LyticsConfiguration
import com.lytics.android.logging.LogLevel

class LyticsDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = LyticsConfiguration(
            apiKey = "at.ecdb4c5684b4c893b8e8fcc70ca0ccc8.4675944e75bd5b2524e3ad5011850521",
            logLevel = LogLevel.DEBUG,
            autoTrackActivityScreens = true,
            autoTrackAppOpens = true,
            autoTrackFragmentScreens = true,
        )
        Lytics.init(applicationContext, config)
    }
}