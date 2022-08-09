package com.iuturakulov.todoapp.model

data class TodoItem(
    val id: String,
    val title: String,
    val taskPriority: TaskPriority,
    val isDone: Boolean,
    val timeTodo: TimeTodo
)
