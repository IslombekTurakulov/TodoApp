package com.iuturakulov.todoapp.data.datasource

import com.iuturakulov.todoapp.data.dao.ItemTasksEntityDao
import com.iuturakulov.todoapp.data.dao.TaskTitleEntityDao
import com.iuturakulov.todoapp.extensions.Result

interface TodoItemLocalDataSource {
    suspend fun getById(id: Long): ItemTasksEntityDao?
    suspend fun getAll():List<ItemTasksEntityDao?>?
    suspend fun insert(item: ItemTasksEntityDao)
    suspend fun update(item: ItemTasksEntityDao)
    suspend fun delete(item: ItemTasksEntityDao)
    suspend fun deleteAll()
    suspend fun getALLTitlesAndIds(): List<TaskTitleEntityDao>?
    suspend fun search(query: String): List<ItemTasksEntityDao>?
    suspend fun insertAll(items: List<ItemTasksEntityDao>) : List<Long>
}