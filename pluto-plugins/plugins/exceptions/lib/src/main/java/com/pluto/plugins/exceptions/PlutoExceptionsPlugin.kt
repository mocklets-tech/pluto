package com.pluto.plugins.exceptions

import android.graphics.Color
import androidx.fragment.app.Fragment
import com.pluto.plugin.DeveloperDetails
import com.pluto.plugin.Plugin
import com.pluto.plugin.PluginConfiguration
import com.pluto.plugins.exceptions.internal.BaseFragment
import com.pluto.plugins.exceptions.internal.persistence.ExceptionDBHandler

class PlutoExceptionsPlugin() : Plugin(ID) {

    @SuppressWarnings("UnusedPrivateMember")
    @Deprecated("Use the default constructor PlutoExceptionsPlugin() instead.")
    constructor(identifier: String) : this()

    override fun getConfig() = PluginConfiguration(
        name = context.getString(R.string.pluto_excep___plugin_name),
        icon = R.drawable.pluto_excep___ic_plugin_logo,
        version = BuildConfig.VERSION_NAME,
        assentColorInt = Color.parseColor("#FF3D00")
    )

    override fun getView(): Fragment = BaseFragment()

    override fun getDeveloperDetails(): DeveloperDetails {
        return DeveloperDetails(
            website = "https://androidpluto.com",
            vcsLink = "https://github.com/androidPluto/pluto",
            twitter = "https://twitter.com/android_pluto"
        )
    }

    override fun onPluginDataCleared() {
        PlutoExceptions.clear()
    }

    override fun onPluginInstalled() {
        ExceptionDBHandler.initialize(context)
        PlutoExceptions.initialize(context, ID)
    }

    companion object {
        const val ID = "exceptions"
    }
}
