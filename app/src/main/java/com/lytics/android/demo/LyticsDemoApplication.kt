package com.lytics.android.demo

import android.app.Application
import com.lytics.android.Lytics
import com.lytics.android.LyticsConfiguration
import com.lytics.android.logging.LogLevel

class LyticsDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = LyticsConfiguration(
            apiKey = "YOUR API KEY HERE",
            logLevel = LogLevel.DEBUG,
            autoTrackActivityScreens = true,
            autoTrackAppOpens = true,
            autoTrackFragmentScreens = true,
        )
        Lytics.init(applicationContext, config)
    }
}