package com.pluto.plugins.datastore.pref.utils

import android.content.Context
import android.util.Log
import com.pluto.plugins.datastore.pref.PlutoDatastoreWatcher
import com.pluto.plugins.datastore.pref.PreferenceHolder
import com.pluto.utilities.list.ListItem
import com.pluto.utilities.views.keyvalue.KeyValuePairEditMetaData

internal class DatastorePrefUtils(context: Context) {

    private val preferences: Preferences = Preferences(context)

    internal var selectedPreferenceFiles: List<PreferenceHolder> = arrayListOf()
        get() = preferences.selectedPreferenceFiles
        set(value) {
            preferences.selectedPreferenceFiles = value
            field = value
        }

    fun get(): List<DatastorePrefKeyValuePair> {
        return emptyList()
    }

    fun set(pair: DatastorePrefKeyValuePair, data: Any) {
        Log.d("", "$pair, $data")
    }

    val allPreferenceFiles: List<PreferenceHolder> = PlutoDatastoreWatcher.sources.value.toList()
}

internal data class DatastorePrefKeyValuePair(
    val key: String,
    val value: Any?,
    val prefLabel: String?,
    val isDefault: Boolean = false
) : ListItem(), KeyValuePairEditMetaData
