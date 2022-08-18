package com.iuturakulov.todoapp.ui.viewmodel.search

import com.iuturakulov.todoapp.model.TodoItem

sealed class SearchResult {
    object Loading : SearchResult()
    object EmptyResult : SearchResult()
    object EmptyQuery : SearchResult()
    data class SuccessResult(val result: List<TodoItem>) : SearchResult()
    data class ErrorResult(val e: Throwable) : SearchResult()
}
