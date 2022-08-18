package com.iuturakulov.todoapp.data.datasource

import com.iuturakulov.todoapp.data.dao.ItemTasksEntity
import com.iuturakulov.todoapp.data.dao.TaskTitleEntityDao
import com.iuturakulov.todoapp.data.dao.TasksDao
import javax.inject.Inject

class TodoItemLocalDataSourceImpl @Inject constructor(private val tasksDao: TasksDao) :
    TodoItemLocalDataSource {
    override suspend fun getById(id: Long): ItemTasksEntity? {
        return tasksDao.getById(id)
    }

    override suspend fun getAll(): List<ItemTasksEntity?>? {
        return tasksDao.getAll()
    }

    override suspend fun insert(item: ItemTasksEntity): Long {
        return tasksDao.insert(item)
    }

    override suspend fun update(item: ItemTasksEntity): Int {
        return tasksDao.update(item)
    }

    override suspend fun delete(item: ItemTasksEntity): Int {
        return tasksDao.delete(item)
    }

    override suspend fun deleteAll() {
        tasksDao.deleteAll()
    }

    override suspend fun getALLTitlesAndIds(): List<TaskTitleEntityDao>? {
        return tasksDao.getAllTitlesAndIds()
    }

    override suspend fun searchTasks(query: String): List<ItemTasksEntity>? {
        return tasksDao.searchTasks(query)
    }

    override suspend fun insertAll(items: List<ItemTasksEntity>): List<Long> {
        return tasksDao.insertAll(items)
    }
}