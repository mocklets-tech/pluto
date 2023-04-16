package com.pluto.tool.modules.ruler.internal.hint

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pluto.R
import com.pluto.tool.modules.ruler.internal.RulerScaleView.Companion.SCALE_GAP

internal class HintViewModel(application: Application) : AndroidViewModel(application) {

    val list: LiveData<List<HintItem>>
        get() = _list
    private val _list = MutableLiveData<List<HintItem>>()

    init {
        generate(getApplication())
    }

    private fun generate(context: Context?) {
        context?.apply {
            val list = arrayListOf<HintItem>()
            list.add(HintItem(getString(R.string.pluto___tool_ruler_hint_reset_position)))
            list.add(HintItem(getString(R.string.pluto___tool_ruler_hint_measure)))
            list.add(HintItem(getString(R.string.pluto___tool_ruler_hint_scale_gap, SCALE_GAP)))
            _list.postValue(list)
        }
    }
}
