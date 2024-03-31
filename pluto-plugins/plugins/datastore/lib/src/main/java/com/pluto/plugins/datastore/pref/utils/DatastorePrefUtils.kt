package com.pluto.plugins.datastore.pref.utils

import android.content.Context
import android.util.Log
import com.pluto.plugins.datastore.pref.ui.DatastorePrefFile
import com.pluto.plugins.datastore.pref.ui.DatastorePrefKeyValuePair

internal class DatastorePrefUtils(context: Context) {

    private val preferences: Preferences = Preferences(context)

    internal var selectedPreferenceFiles: List<DatastorePrefFile> = arrayListOf()
        get() {
            return emptyList()
        }
        set(value) {
            preferences.selectedPreferenceFiles = emptyList<DatastorePrefFile>().toString()
            field = value
        }

    fun get(): List<DatastorePrefKeyValuePair> {
        return emptyList()
    }

    fun set(pair: DatastorePrefKeyValuePair, data: Any) {
        Log.d("", "$pair, $data")
    }

    val allPreferenceFiles: List<DatastorePrefFile> = emptyList()
}
