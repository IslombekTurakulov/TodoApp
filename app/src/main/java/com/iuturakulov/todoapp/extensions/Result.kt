package com.iuturakulov.todoapp.extensions

sealed class Result<out S, out E> {
    data class Success<out S>(val result: S) : Result<S, Nothing>()
    data class Error<out E>(val result: E) : Result<Nothing, E>()
}