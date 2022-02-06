package com.sampleapp

import android.app.Application
import com.pluto.Pluto
import com.pluto.logger.PlutoLoggerPlugin

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Pluto.Installer(this)
            .addPlugin(PlutoLoggerPlugin("logger"))
//            .addPlugin(PlutoSharePreferencesPlugin("sharedPref"))
            .install()
        Pluto.showNotch(false)
    }
}
