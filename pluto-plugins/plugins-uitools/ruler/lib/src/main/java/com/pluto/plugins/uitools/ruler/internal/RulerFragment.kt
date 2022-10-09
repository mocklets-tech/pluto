package com.pluto.plugins.uitools.ruler.internal

import android.view.View
import com.pluto.plugins.uitools.FullScreenTool
import com.pluto.utilities.extensions.toast

internal class RulerFragment : FullScreenTool() {

    override fun getToolView(): View {
        val view = RulerScaleView(requireContext())
        view.isClickable = true
        return view
    }

    override fun onHintClicked() {
        requireContext().toast("hint clicked")
    }

    override fun onCloseClicked() {
    }
}