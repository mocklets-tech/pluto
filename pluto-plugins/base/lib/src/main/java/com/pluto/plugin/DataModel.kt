package com.pluto.plugin

import androidx.annotation.DrawableRes

data class DeveloperDetails(
    val vcsLink: String? = null,
    val website: String? = null,
    val twitter: String? = null
)

data class PluginConfiguration(
    val name: String,
    @DrawableRes val icon: Int = R.drawable.pluto___ic_plugin_placeholder_icon,
    val version: String
)

data class PluginGroupConfiguration(
    val name: String,
    @DrawableRes val icon: Int = R.drawable.pluto___ic_plugin_group_placeholder_icon,
)
