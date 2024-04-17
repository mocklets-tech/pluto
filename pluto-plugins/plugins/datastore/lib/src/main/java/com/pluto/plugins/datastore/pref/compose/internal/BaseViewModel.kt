package com.pluto.plugins.datastore.pref.compose.internal

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.pluto.plugins.datastore.pref.PlutoDatastoreWatcher
import com.pluto.plugins.datastore.pref.PreferenceHolder
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
internal class BaseViewModel(application: Application) : AndroidViewModel(application) {

    internal val output = MutableStateFlow<List<PrefUiModel>>(listOf())
    internal val filteredPref = MutableStateFlow<Set<PreferenceHolder>>(emptySet())
//    internal val showFilterView: MutableStateFlow<Boolean> = MutableStateFlow(false)
//    private val expandedMap = mutableMapOf<String, MutableState<Boolean>>()

    init {
        viewModelScope.launch {
            PlutoDatastoreWatcher.sources.map { list ->
//                filteredPref.value = list.toList()
//                filteredPref.value = list.associate {
//                    it.name to (filteredPref.value[it.name] ?: true)
//                }
                filteredPref.value = list
                list.map { prefHolder ->
                    prefHolder.preferences.data.map { pref ->
                        pref to prefHolder.name
                    }
                }
            }.map { listFlows ->
                combine(listFlows) { listPreferences ->
                    listPreferences.map { namePrefPair ->
                        PrefUiModel(
                            name = namePrefPair.second,
                            data = namePrefPair.first.asMap().map { entry ->
                                PrefElement(
                                    key = entry.key.toString(),
                                    value = entry.value,
                                    prefName = namePrefPair.second
                                )
                            },
//                            isExpanded = expandedMap.getOrPut(namePrefPair.second) {
//                                mutableStateOf(true)
//                            }
                        )
                    }
                }
            }.flattenMerge().combine(filteredPref) { prefList, filterList ->
                prefList.filter { uiModel ->
                    filterList.find { it.name == uiModel.name }?.let {
                        true
                    } ?: run { false }
                }
            }.collect { list ->
                output.value = list
            }
        }
    }

    val updateValue: (PrefElement, String) -> Unit = { preferenceElement, value ->
        viewModelScope.launch {
            val preferences = PlutoDatastoreWatcher.sources.value.find {
                it.name == preferenceElement.prefName
            }?.preferences
            preferences?.edit { preference ->
                when {
                    preferenceElement.type == Type.TypeBoolean && value.toBooleanStrictOrNull() != null ->
                        preference[booleanPreferencesKey(preferenceElement.key)] = value.toBoolean()

                    preferenceElement.type == Type.TypeDouble && value.toDoubleOrNull() != null ->
                        preference[doublePreferencesKey(preferenceElement.key)] = value.toDouble()

                    preferenceElement.type == Type.TypeFloat && value.toFloatOrNull() != null ->
                        preference[floatPreferencesKey(preferenceElement.key)] = value.toFloat()

                    preferenceElement.type == Type.TypeLong && value.toLongOrNull() != null ->
                        preference[longPreferencesKey(preferenceElement.key)] = value.toLong()

                    preferenceElement.type == Type.TypeString ->
                        preference[stringPreferencesKey(preferenceElement.key)] = value

                    else -> {
                        // show some error
                        // add validation before sending data here
                    }
                }
            }
        }
    }
}
