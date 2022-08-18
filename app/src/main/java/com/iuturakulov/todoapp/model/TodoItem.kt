package com.iuturakulov.todoapp.model

import com.iuturakulov.todoapp.data.TaskPriority

data class TodoItem(
    val id: String,
    val title: TitleTask,
    val description: DescriptionTask,
    val taskPriority: TaskPriority,
    val isDone: Boolean,
    val timeTodo: TimeTodo
) {

    @JvmInline
    value class TitleTask(val title: String) {
        fun validate(): Boolean {
            return if (title != null && title.isNotEmpty()) {
                true
            } else {
                false
            }
        }
    }

    @JvmInline
    value class DescriptionTask(val description: String) {
        fun validate(): Boolean {
            return if (description != null && description.isNotEmpty()) {
                true
            } else {
                false
            }
        }
    }
}


