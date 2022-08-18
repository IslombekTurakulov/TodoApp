package com.iuturakulov.todoapp.data.repository

import com.iuturakulov.todoapp.data.TaskPriority
import com.iuturakulov.todoapp.data.datasource.TodoItemLocalDataSource
import com.iuturakulov.todoapp.data.datasource.TodoItemLocalDataSourceImpl
import com.iuturakulov.todoapp.extensions.Result
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(private val localDataSource: TodoItemLocalDataSource) : TodoItemsRepository {
    override suspend fun getTodoItem(id: Long): Result<TodoItem, Throwable?> {
       val task = localDataSource.getById(id)
        return if (task != null) {
            Result.Success(task.toModel())
        } else {
            Result.Error(Throwable("TodoItem not found"))
        }
    }

    override suspend fun getPriorities(): Result<List<TaskPriority>, Throwable?> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodoItems(): Result<List<TodoItem>, Throwable?> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllTitlesAndIds(): Result<List<TodoItemId>, Throwable?> {
        TODO("Not yet implemented")
    }

    override suspend fun searchTodoItems(query: String): Result<List<TodoItem>, Throwable?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodoItem(TodoItem: TodoItem): Result<Long, Throwable?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoItem(TodoItem: TodoItem): Result<Int, Throwable?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoItem(TodoItem: TodoItem): Result<Int, Throwable?> {
        TODO("Not yet implemented")
    }
}