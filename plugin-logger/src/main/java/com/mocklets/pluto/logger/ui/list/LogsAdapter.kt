package com.mocklets.pluto.logger.ui.list

import android.view.ViewGroup
import com.mocklets.pluto.logger.LogData
import com.mocklets.pluto.utilities.list.BaseAdapter
import com.mocklets.pluto.utilities.list.DiffAwareHolder
import com.mocklets.pluto.utilities.list.ListItem

internal class LogsAdapter(private val listener: OnActionListener) : BaseAdapter() {
    override fun getItemViewType(item: ListItem): Int? {
        return when (item) {
            is LogData -> ITEM_TYPE_LOG
            else -> null
        }
    }

    override fun onViewHolderCreated(parent: ViewGroup, viewType: Int): DiffAwareHolder? {
        return when (viewType) {
            ITEM_TYPE_LOG -> LogItemHolder(parent, listener)
            else -> null
        }
    }

    companion object {
        const val ITEM_TYPE_LOG = 1000
    }
}
