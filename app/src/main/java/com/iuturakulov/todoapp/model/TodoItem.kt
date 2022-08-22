package com.iuturakulov.todoapp.model

import com.iuturakulov.todoapp.data.dao.TaskPriorities

data class TodoItem(
    val id: Long,
    val title: TitleTask,
    val description: DescriptionTask,
    val taskPriority: TaskPriorities,
    val isDone: Boolean,
    val deadline: Long? = null,
    val created: Long,
    val updated: Long? = null
) {

    @JvmInline
    value class TitleTask(val value: String) {
        companion object {
            fun validate(title: String?): TitleTask? {
                return if (title != null
                    && title.isNotBlank()
                ) {
                    TitleTask(title)
                } else {
                    null
                }
            }
        }
    }

    @JvmInline
    value class DescriptionTask(val value: String) {
        companion object {
            fun validate(description: String?): TitleTask? {
                return if (description != null) {
                    TitleTask(description)
                } else {
                    null
                }
            }
        }
    }
}


