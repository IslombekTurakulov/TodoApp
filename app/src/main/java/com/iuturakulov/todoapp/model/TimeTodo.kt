package com.iuturakulov.todoapp.model

data class TimeTodo(
    val deadline: Long? = null,
    val created: Long,
    val updated: Long? = null
)
