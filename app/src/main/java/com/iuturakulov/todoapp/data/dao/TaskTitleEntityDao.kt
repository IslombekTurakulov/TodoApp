package com.iuturakulov.todoapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_ID
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_TITLE

data class TaskTitleEntityDao(
    @ColumnInfo(name = COLUMN_TASK_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_TASK_TITLE)
    val title: String
)