package com.iuturakulov.todoapp.data.repository

import com.iuturakulov.todoapp.model.TodoItem

interface TodoItemsRepository {
    suspend fun getTask(id: Long): Result<Task, Throwable?>
    suspend fun getCategories(): Result<List<Category>, Throwable?>
    suspend fun getTasks(): Result<List<Task>, Throwable?>
    suspend fun getAllTitlesAndIds(): Result<List<TaskTitleId>, Throwable?>
    suspend fun searchTasks(query: String): Result<List<Task>, Throwable?>
    suspend fun insertTask(task: Task): Result<Long, Throwable?>
    suspend fun updateTask(task: Task): Result<Int, Throwable?>
    suspend fun deleteTask(task: Task): Result<Int, Throwable?>
}