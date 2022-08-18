package com.iuturakulov.todoapp.model

data class TaskPriority(
    val id: Long,
    val title: String,
    val tasks: List<TodoItem>
)