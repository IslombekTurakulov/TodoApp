package com.iuturakulov.todoapp.extensions

import com.iuturakulov.todoapp.model.TodoItem

fun List<TodoItem>.toListAll(): List<TodoItem> = this.filter { it.isDone.not() }
fun List<TodoItem>.toListOverdue(): List<TodoItem> = this.filter { it.isDone }