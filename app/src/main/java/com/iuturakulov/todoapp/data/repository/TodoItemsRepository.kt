package com.iuturakulov.todoapp.data.repository

import com.iuturakulov.todoapp.extensions.Result
import com.iuturakulov.todoapp.model.TaskPriority
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId

interface TodoItemsRepository {
    suspend fun getTask(id: Long): Result<TodoItem, Throwable?>
    suspend fun getPriorities(): Result<List<TaskPriority>, Throwable?>
    suspend fun getTasks(): Result<List<TodoItem>, Throwable?>
    suspend fun getAllTitlesAndIds(): Result<List<TodoItemId>, Throwable?>
    suspend fun searchTasks(query: String): Result<List<TodoItem>, Throwable?>
    suspend fun insertTask(todoItem: TodoItem): Result<Long, Throwable?>
    suspend fun updateTask(todoItem: TodoItem): Result<Int, Throwable?>
    suspend fun deleteTask(todoItem: TodoItem): Result<Int, Throwable?>
}