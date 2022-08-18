package com.iuturakulov.todoapp.data.dao

import androidx.room.ColumnInfo

data class TaskTitleEntityDao(
    @ColumnInfo(name = COLUMN_TASK_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_TASK_TITLE)
    val title: String
) {
    companion object {
        const val COLUMN_TASK_ID = "task_id"
        const val COLUMN_TASK_TITLE = "title"
    }
}