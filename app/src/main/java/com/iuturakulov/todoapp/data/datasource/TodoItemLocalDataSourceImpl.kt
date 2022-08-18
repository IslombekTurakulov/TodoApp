package com.iuturakulov.todoapp.data.datasource

import com.iuturakulov.todoapp.data.dao.ItemTasksEntityDao
import com.iuturakulov.todoapp.data.dao.TaskTitleEntityDao
import com.iuturakulov.todoapp.data.dao.TasksDao
import javax.inject.Inject

class TodoItemLocalDataSourceImpl @Inject constructor(private val tasksDao: TasksDao) :
    TodoItemLocalDataSource {
    override suspend fun getById(id: Long): ItemTasksEntityDao? {
        return tasksDao.getById(id)
    }

    override suspend fun getAll(): List<ItemTasksEntityDao?>? {
        return tasksDao.getAll()
    }

    override suspend fun insert(item: ItemTasksEntityDao): Long {
        return tasksDao.insert(item)
    }

    override suspend fun update(item: ItemTasksEntityDao): Int {
        return tasksDao.update(item)
    }

    override suspend fun delete(item: ItemTasksEntityDao): Int {
        return tasksDao.delete(item)
    }

    override suspend fun deleteAll() {
        tasksDao.deleteAll()
    }

    override suspend fun getALLTitlesAndIds(): List<TaskTitleEntityDao>? {
        return tasksDao.getAllTitlesAndIds()
    }

    override suspend fun search(query: String): List<ItemTasksEntityDao>? {
        return tasksDao.searchTasks(query)
    }

    override suspend fun insertAll(items: List<ItemTasksEntityDao>): List<Long> {
        return tasksDao.insertAll(items)
    }
}