package com.iuturakulov.todoapp.data.repository

import com.iuturakulov.todoapp.data.datasource.TodoItemLocalDataSource
import com.iuturakulov.todoapp.extensions.Result
import com.iuturakulov.todoapp.extensions.toCategories
import com.iuturakulov.todoapp.extensions.toEntity
import com.iuturakulov.todoapp.extensions.toModel
import com.iuturakulov.todoapp.model.TaskPriority
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(private val localDataSource: TodoItemLocalDataSource) :
    TodoItemsRepository {
    override suspend fun getTodoItem(id: Long): Result<TodoItem, Throwable?> {
        val task = localDataSource.getById(id)
        return if (task != null) {
            Result.Success(task.toModel())
        } else {
            Result.Error(Throwable("TodoItem not found"))
        }
    }

    override suspend fun getPriorities(): Result<List<TaskPriority>, Throwable?> {
        val priority = localDataSource.getAll()
        return if (priority != null) {
            Result.Success(priority.toCategories())
        } else {
            Result.Error(Throwable("Priorities not found"))
        }
    }

    override suspend fun getTodoItems(): Result<List<TodoItem>, Throwable?> {
        val todoItems = localDataSource.getAll()
        return if (todoItems != null) {
            Result.Success(todoItems.map { it?.toModel()!! })
        } else {
            Result.Error(Throwable("TodoItems not found"))
        }
    }

    override suspend fun getAllTitlesAndIds(): Result<List<TodoItemId>, Throwable?> {
        val todoItems = localDataSource.getALLTitlesAndIds()
        return if (todoItems != null) {
            Result.Success(todoItems.map { it.toModel() }.map { TodoItemId(it.id, it.title) })
        } else {
            Result.Error(Throwable("TodoItems not found"))
        }
    }

    override suspend fun searchTodoItems(query: String): Result<List<TodoItem>, Throwable?> {
        val todoItems = localDataSource.search("%$query%")
        return if (todoItems != null) {
            Result.Success(todoItems.map { it.toModel() })
        } else {
            Result.Error(Throwable("TodoItems not found"))
        }
    }

    override suspend fun insertTodoItem(task: TodoItem): Result<Long, Throwable?> {
        val request = localDataSource.insert(task.toEntity())
        val result = if (request != -1L) {
            Result.Success(request)
        } else {
            Result.Error(null)
        }
        return result
    }

    override suspend fun updateTodoItem(TodoItem: TodoItem): Result<Int, Throwable?> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoItem(TodoItem: TodoItem): Result<Int, Throwable?> {
        TODO("Not yet implemented")
    }
}