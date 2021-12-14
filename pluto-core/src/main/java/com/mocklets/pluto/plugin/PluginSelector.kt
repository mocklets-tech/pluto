package com.mocklets.pluto.plugin

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mocklets.pluto.Pluto
import com.mocklets.pluto.R
import com.mocklets.pluto.applifecycle.AppState
import com.mocklets.pluto.databinding.PlutoLayoutPluginSelectorBinding
import com.mocklets.pluto.notch.Notch
import com.mocklets.pluto.ui.PluginAdapter
import com.mocklets.pluto.utilities.list.BaseAdapter
import com.mocklets.pluto.utilities.list.DiffAwareAdapter
import com.mocklets.pluto.utilities.list.DiffAwareHolder
import com.mocklets.pluto.utilities.list.ListItem
import com.mocklets.pluto.utilities.viewBinding

internal class PluginSelector : DialogFragment() {

    private var notch: Notch? = null
    private val binding by viewBinding(PlutoLayoutPluginSelectorBinding::bind)
    private val pluginsViewModel by activityViewModels<PluginsViewModel>()
    private val pluginAdapter: BaseAdapter by lazy { PluginAdapter(onActionListener) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.pluto___layout_plugin_selector, container, false)

    override fun getTheme(): Int = R.style.PlutoBottomSheetDialogTheme

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.apply {
            adapter = pluginAdapter
        }

        Pluto.appState.removeObserver(appStateListener)
        Pluto.appState.observe(viewLifecycleOwner, appStateListener)

        pluginsViewModel.plugins.removeObserver(pluginListObserver)
        pluginsViewModel.plugins.observe(viewLifecycleOwner, pluginListObserver)
    }

    fun show(it: AppCompatActivity, notch: Notch?) {
        this.notch = notch
        show(it.supportFragmentManager, FRAGMENT_TAG)
    }

    private val pluginListObserver = Observer<List<Plugin>> {
        pluginAdapter.list = it
    }

    private val appStateListener = Observer<AppState> {
        if (it is AppState.Background) {
            dismiss()
        }
    }

    private val onActionListener = object : DiffAwareAdapter.OnActionListener {
        override fun onAction(action: String, data: ListItem, holder: DiffAwareHolder?) {
//            if (data is Plugin) {
//
//            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            notch?.remove()
        }
        dialog.window?.setWindowAnimations(R.style.PlutoPluginSelectorAnimation)
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        notch?.add()
    }

    private companion object {
        const val FRAGMENT_TAG = "plugin_selector"
    }
}
