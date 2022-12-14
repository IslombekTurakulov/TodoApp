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
    override suspend fun getTask(id: Long): Result<TodoItem, Throwable?> {
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

    override suspend fun getTasks(): Result<List<TodoItem>, Throwable?> {
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

    override suspend fun searchTasks(query: String): Result<List<TodoItem>, Throwable?> {
        val todoItems = localDataSource.searchTasks("%$query%")
        return if (todoItems != null) {
            Result.Success(todoItems.map { it.toModel() })
        } else {
            Result.Error(Throwable("TodoItems not found"))
        }
    }

    override suspend fun insertTask(todoItem: TodoItem): Result<Long, Throwable?> {
        val request = localDataSource.insert(todoItem.toEntity())
        return if (request != -1L) {
            Result.Success(request)
        } else {
            Result.Error(null)
        }
    }

    override suspend fun updateTask(todoItem: TodoItem): Result<Int, Throwable?> {
        val request = localDataSource.update(todoItem.toEntity())
        return if (request != 0) {
            Result.Success(request)
        } else {
            Result.Error(null)
        }
    }

    override suspend fun deleteTask(todoItem: TodoItem): Result<Int, Throwable?> {
        val request = localDataSource.delete(todoItem.toEntity())
        return if (request != 0) {
            Result.Success(request)
        } else {
            Result.Error(null)
        }
    }
}