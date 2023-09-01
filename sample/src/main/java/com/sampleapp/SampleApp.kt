package com.sampleapp

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.os.StrictMode.VmPolicy
import android.util.Log
import com.demo.plugin.DemoPlugin
import com.pluto.Pluto
import com.pluto.plugins.datastore.pref.PlutoDatastoreWatcher
import com.pluto.plugins.exceptions.PlutoExceptions
import com.pluto.plugins.exceptions.PlutoExceptionsPlugin
import com.pluto.plugins.layoutinspector.PlutoLayoutInspectorPlugin
import com.pluto.plugins.logger.PlutoLoggerPlugin
import com.pluto.plugins.logger.PlutoTimberTree
import com.pluto.plugins.network.PlutoNetworkPlugin
import com.pluto.plugins.rooms.db.PlutoRoomsDBWatcher
import com.sampleapp.functions.datastore.DemoDatastorePrefFragment.Companion.APP_STATE_PREF_NAME
import com.sampleapp.functions.datastore.DemoDatastorePrefFragment.Companion.USER_STATE_PREF_NAME
import com.sampleapp.functions.datastore.appStateDatastore
import com.sampleapp.functions.datastore.userStateDatastore
import com.sampleapp.functions.roomsdatabase.db.SampleDatabase
import com.sampleapp.functions.roomsdatabase.db2.Sample2Database
import com.sampleapp.pluto.DataSourcePluginGroup
import kotlin.system.exitProcess
import timber.log.Timber

class SampleApp : Application() {

    override fun onCreate() {
        initializeStrictMode()
        super.onCreate()
        Pluto.Installer(this)
            .addPlugin(DemoPlugin())
            .addPlugin(PlutoExceptionsPlugin())
//            .addPlugin(PlutoDatastorePreferencesPlugin())
            .addPlugin(PlutoNetworkPlugin())
            .addPlugin(PlutoLoggerPlugin())
//            .addPlugin(PlutoSharePreferencesPlugin())
//            .addPlugin(PlutoRoomsDatabasePlugin())
            .addPlugin(PlutoLayoutInspectorPlugin())
            .addPluginGroup(DataSourcePluginGroup())
            .install()
        Pluto.showNotch(true)

        plantPlutoTimber()
        setExceptionListener()
        watchRoomsDatabase()
        watchDatastorePreferences()
    }

    private fun initializeStrictMode() {
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .permitDiskReads()
                .detectDiskWrites()
                .detectNetwork() // or .detectAll() for all detectable problems
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )
    }

    /**
     * Datastore Preferences handler
     */
    private fun watchDatastorePreferences() {
        PlutoDatastoreWatcher.watch(APP_STATE_PREF_NAME, appStateDatastore)
        PlutoDatastoreWatcher.watch(USER_STATE_PREF_NAME, userStateDatastore)
    }

    /**
     * Rooms database handler
     */
    private fun watchRoomsDatabase() {
        PlutoRoomsDBWatcher.watch(SampleDatabase.DB_NAME, SampleDatabase::class.java)
        PlutoRoomsDBWatcher.watch(Sample2Database.DB_NAME, Sample2Database::class.java)
    }

    /**
     * Logger Timber handler
     */
    private fun plantPlutoTimber() {
        Timber.plant(PlutoTimberTree())
    }

    /**
     * Exception handler
     */
    private fun setExceptionListener() {
        PlutoExceptions.setExceptionHandler { thread, throwable ->
            Log.e("exception_demo", "uncaught exception handled on thread: " + thread.name, throwable)
            exitProcess(0)
        }

        PlutoExceptions.setANRHandler { thread, exception ->
            Log.e("anr_demo", "unhandled ANR handled on thread: " + thread.name, exception)
        }
    }
}
