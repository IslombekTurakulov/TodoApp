package com.iuturakulov.todoapp

data class TodoItem(
    val id: String,
    val title: String,
    val taskPriority: TaskPriority,
    val deadline: String? = null,
    val isDone: Boolean,
    val created: String,
    val updated: String? = null
)
