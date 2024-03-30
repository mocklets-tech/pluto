package com.pluto.plugins.preferences.utils

import android.content.Context

internal class Preferences(context: Context) {

    private val settingsPrefs by lazy { context.preferences("_pluto_pref_settings") }

    internal var selectedPreferenceFiles: String?
        get() = settingsPrefs.getString(SELECTED_PREF_FILE, null)
        set(value) = settingsPrefs.edit().putString(SELECTED_PREF_FILE, value).apply()

    companion object {
        private const val SELECTED_PREF_FILE = "selected_pref_file_v2"
    }
}

private fun Context.preferences(name: String, mode: Int = Context.MODE_PRIVATE) = getSharedPreferences(name, mode)
