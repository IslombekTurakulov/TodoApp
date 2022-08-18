package com.iuturakulov.todoapp.data.repository

import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import com.iuturakulov.todoapp.extensions.Result
import com.iuturakulov.todoapp.model.TaskPriority

interface TodoItemsRepository {
    suspend fun getById(id: Long): Result<TodoItem, Throwable?>
    suspend fun getPriorities(): Result<List<TaskPriority>, Throwable?>
    suspend fun getAllTasks(): Result<List<TodoItem>, Throwable?>
    suspend fun getAllTitlesAndIds(): Result<List<TodoItemId>, Throwable?>
    suspend fun searchTasks(query: String): Result<List<TodoItem>, Throwable?>
    suspend fun insertTask(TodoItem: TodoItem): Result<Long, Throwable?>
    suspend fun updateTask(TodoItem: TodoItem): Result<Int, Throwable?>
    suspend fun deleteTask(TodoItem: TodoItem): Result<Int, Throwable?>
}