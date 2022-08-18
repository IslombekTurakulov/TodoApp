package com.iuturakulov.todoapp.ui.viewmodel.explore

import com.bumptech.glide.Priority

sealed class PrioritiesResult {
    object Loading : PrioritiesResult()
    object EmptyResult : PrioritiesResult()
    data class SuccessResult(val result: List<Priority>) : PrioritiesResult()
    data class ErrorResult(val e: Throwable) : PrioritiesResult()
}
