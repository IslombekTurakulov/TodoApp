package com.iuturakulov.todoapp.ui.viewmodel.details

import com.iuturakulov.todoapp.model.TodoItem

sealed class FetchDetailsState {

    object Loading : FetchDetailsState()

    data class Result(val task: TodoItem) : FetchDetailsState()

    data class Error(val error: Throwable) : FetchDetailsState()
}
