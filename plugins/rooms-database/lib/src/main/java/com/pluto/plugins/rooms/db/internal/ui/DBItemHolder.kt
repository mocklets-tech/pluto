package com.pluto.plugins.rooms.db.internal.ui

import android.view.ViewGroup
import com.pluto.plugin.utilities.extensions.inflate
import com.pluto.plugin.utilities.list.DiffAwareAdapter
import com.pluto.plugin.utilities.list.DiffAwareHolder
import com.pluto.plugin.utilities.list.ListItem
import com.pluto.plugins.rooms.db.R
import com.pluto.plugins.rooms.db.databinding.PlutoRoomsItemDbSelectorBinding

internal class DBItemHolder(
    parent: ViewGroup,
    actionListener: DiffAwareAdapter.OnActionListener
) : DiffAwareHolder(parent.inflate(R.layout.pluto_rooms___item_db_selector), actionListener) {

    private val binding = PlutoRoomsItemDbSelectorBinding.bind(itemView)
    private val key = binding.key
    private val value = binding.value
    private val file = binding.file

    override fun onBind(item: ListItem) {
    }
}
