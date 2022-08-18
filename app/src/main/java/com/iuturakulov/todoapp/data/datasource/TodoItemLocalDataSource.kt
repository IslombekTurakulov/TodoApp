package com.iuturakulov.todoapp.data.datasource

import com.iuturakulov.todoapp.data.dao.ItemTasksEntity
import com.iuturakulov.todoapp.data.dao.TaskTitleEntityDao

interface TodoItemLocalDataSource {
    suspend fun getById(id: Long): ItemTasksEntity?
    suspend fun getAll(): List<ItemTasksEntity?>?
    suspend fun insert(item: ItemTasksEntity): Long
    suspend fun update(item: ItemTasksEntity): Int
    suspend fun delete(item: ItemTasksEntity): Int
    suspend fun deleteAll()
    suspend fun getALLTitlesAndIds(): List<TaskTitleEntityDao>?
    suspend fun searchTasks(query: String): List<ItemTasksEntity>?
    suspend fun insertAll(items: List<ItemTasksEntity>): List<Long>
}