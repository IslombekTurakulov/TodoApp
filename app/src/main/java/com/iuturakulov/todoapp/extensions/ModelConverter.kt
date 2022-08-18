package com.iuturakulov.todoapp.extensions

import com.iuturakulov.todoapp.data.dao.ItemTasksEntityDao
import com.iuturakulov.todoapp.model.TodoItem

fun ItemTasksEntityDao.toModel() : TodoItem {
    return TodoItem(
        id = this.id,
        title = this.title,
        description = this.description,
        isDone = this.isDone
    )
}