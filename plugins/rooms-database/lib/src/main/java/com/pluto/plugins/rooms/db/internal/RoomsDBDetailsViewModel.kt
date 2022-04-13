package com.pluto.plugins.rooms.db.internal

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.RoomDatabase
import com.pluto.plugins.rooms.db.internal.core.isSystemTable
import com.pluto.plugins.rooms.db.internal.core.query.QueryBuilder
import com.pluto.plugins.rooms.db.internal.core.query.QueryExecutor
import java.lang.Exception

internal class RoomsDBDetailsViewModel(application: Application) : AndroidViewModel(application) {

    val tables: LiveData<Pair<List<TableModel>, Exception?>>
        get() = _tables
    private val _tables = MutableLiveData<Pair<List<TableModel>, Exception?>>()

    val currentTable: LiveData<TableModel?>
        get() = _currentTable
    private val _currentTable = MutableLiveData<TableModel?>()

    fun init(context: Context, name: String, dbClass: Class<out RoomDatabase>) {
        QueryExecutor.init(context, name, dbClass)
        fetchTables()
    }

    private fun fetchTables() {
        val tables = arrayListOf<String>()
        QueryExecutor.query(
            QueryBuilder.GET_TABLE_NAMES,
            {
                it.second.forEach { list ->
                    tables.addAll(list)
                }

                val processedTableList = arrayListOf<TableModel>()
                val processedSystemTableList = arrayListOf<TableModel>()
                tables.forEach { table ->
                    if (isSystemTable(table)) {
                        processedSystemTableList.add(TableModel(table, true))
                    } else {
                        processedTableList.add(TableModel(table, false))
                    }
                }
                if (processedTableList.size == 1) {
                    _currentTable.postValue(processedTableList.first())
                } else {
                    _currentTable.postValue(null)
                }
                _tables.postValue(Pair(processedTableList.plus(processedSystemTableList), null))
            },
            {
                _tables.postValue(Pair(emptyList(), it))
            }
        )
    }

    fun selectTable(table: TableModel) {
        _currentTable.postValue(table)
    }
}
