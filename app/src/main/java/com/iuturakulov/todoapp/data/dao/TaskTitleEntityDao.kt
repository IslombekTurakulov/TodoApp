package com.iuturakulov.todoapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class TaskTitleEntityDao(
    @ColumnInfo(name = ItemTasksEntity.COLUMN_TASK_ID)
    val id: Long,
    @ColumnInfo(name = ItemTasksEntity.COLUMN_TASK_TITLE)
    val title: String
)