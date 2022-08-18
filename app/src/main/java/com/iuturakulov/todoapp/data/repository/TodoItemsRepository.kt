package com.iuturakulov.todoapp.data.repository

import com.iuturakulov.todoapp.data.TaskPriorities
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import com.iuturakulov.todoapp.extensions.Result
import com.iuturakulov.todoapp.model.TaskPriority

interface TodoItemsRepository {
    suspend fun getTodoItem(id: Long): Result<TodoItem, Throwable?>
    suspend fun getPriorities(): Result<List<TaskPriority>, Throwable?>
    suspend fun getTodoItems(): Result<List<TodoItem>, Throwable?>
    suspend fun getAllTitlesAndIds(): Result<List<TodoItemId>, Throwable?>
    suspend fun searchTodoItems(query: String): Result<List<TodoItem>, Throwable?>
    suspend fun insertTodoItem(TodoItem: TodoItem): Result<Long, Throwable?>
    suspend fun updateTodoItem(TodoItem: TodoItem): Result<Int, Throwable?>
    suspend fun deleteTodoItem(TodoItem: TodoItem): Result<Int, Throwable?>
}