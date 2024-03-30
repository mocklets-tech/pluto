package com.pluto.plugins.preferences.ui

import androidx.annotation.Keep
import com.pluto.utilities.list.ListItem
import com.pluto.utilities.selector.SelectorOption
import com.pluto.utilities.views.keyvalue.KeyValuePairEditMetaData
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
internal data class SharedPrefFile(
    val label: CharSequence,
    val isDefault: Boolean
) : SelectorOption() {
    override fun displayText(): CharSequence = label
}

internal data class SharedPrefKeyValuePair(
    val key: String,
    val value: Any?,
    val prefLabel: String?,
    val isDefault: Boolean = false
) : ListItem(), KeyValuePairEditMetaData
