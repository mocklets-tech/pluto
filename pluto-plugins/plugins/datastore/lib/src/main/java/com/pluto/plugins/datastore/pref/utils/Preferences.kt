package com.pluto.plugins.datastore.pref.utils

import android.content.Context
import com.pluto.plugins.datastore.pref.PlutoDatastoreWatcher
import com.pluto.plugins.datastore.pref.PreferenceHolder
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

internal class Preferences(context: Context) {

    private val settingsPrefs by lazy { context.preferences("_pluto_pref_datastore_settings") }
    private val moshi: Moshi = Moshi.Builder().build()
    private val moshiAdapter: JsonAdapter<List<String>?> = moshi.adapter(Types.newParameterizedType(List::class.java, String::class.java))

    internal var selectedPreferenceFiles: List<PreferenceHolder>
        get() = settingsPrefs.getString(SELECTED_PREF_FILE, null)?.let {
            moshiAdapter.fromJson(it)?.map { label -> PlutoDatastoreWatcher.getSource(label) }
        } ?: run { emptyList() }
        set(value) = settingsPrefs.edit().putString(SELECTED_PREF_FILE, moshiAdapter.toJson(value.map { it.name })).apply()

    companion object {
        private const val SELECTED_PREF_FILE = "selected_datastore_pref_file"
    }
}

private fun Context.preferences(name: String, mode: Int = Context.MODE_PRIVATE) = getSharedPreferences(name, mode)
