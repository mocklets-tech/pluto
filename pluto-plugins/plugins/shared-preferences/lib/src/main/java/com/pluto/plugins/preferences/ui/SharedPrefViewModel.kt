package com.pluto.plugins.preferences.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pluto.plugins.preferences.utils.SharedPrefUtils
import com.pluto.utilities.selector.SelectorOption

internal class SharedPrefViewModel(application: Application) : AndroidViewModel(application) {

    val preferenceList: LiveData<List<SharedPrefKeyValuePair>>
        get() = _preferences
    private val _preferences = MutableLiveData<List<SharedPrefKeyValuePair>>()

    private val sharePrefUtils = SharedPrefUtils(application.applicationContext)

    fun refresh() {
        _preferences.postValue(sharePrefUtils.get())
    }

    fun getSelectedPrefFiles(): List<SelectorOption> = sharePrefUtils.selectedPreferenceFiles

    fun setSelectedPrefFiles(files: List<SharedPrefFile>) {
        sharePrefUtils.selectedPreferenceFiles = files
        refresh()
    }

    fun setPrefData(pair: SharedPrefKeyValuePair, data: Any) {
        sharePrefUtils.set(pair, data)
        refresh()
    }
}
