package com.iuturakulov.todoapp.ui.viewmodel.explore

import com.iuturakulov.todoapp.model.TodoItem

sealed class TasksResult {
    object Loading : TasksResult()
    object EmptyResult : TasksResult()
    data class SuccessResult(val result: List<TodoItem>) : TasksResult()
    data class ErrorResult(val e: Throwable) : TasksResult()
}
