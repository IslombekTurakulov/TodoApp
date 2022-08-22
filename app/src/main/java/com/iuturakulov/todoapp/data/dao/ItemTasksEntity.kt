package com.iuturakulov.todoapp.data.dao

import androidx.room.*
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_CREATED
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_DATE
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_DESCRIPTION
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_DONE
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_ID
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_PRIORITY
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_TITLE
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_UPDATED
import com.iuturakulov.todoapp.extensions.Constants.TABLE_NAME
import com.iuturakulov.todoapp.extensions.EnumConverter
import com.iuturakulov.todoapp.model.TodoItem
import javax.inject.Inject

@Entity(
    tableName = TABLE_NAME,
    indices = [Index(
        value = [COLUMN_TASK_TITLE, COLUMN_TASK_PRIORITY],
        unique = true
    )]
)
@TypeConverters(EnumConverter::class)
data class ItemTasksEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_TASK_ID)
    val id: Long,

    @ColumnInfo(name = COLUMN_TASK_TITLE)
    val title: TodoItem.TitleTask,

    @ColumnInfo(name = COLUMN_TASK_DESCRIPTION)
    val description: TodoItem.DescriptionTask,

    @ColumnInfo(name = COLUMN_TASK_PRIORITY)
    val priority: TaskPriorities,

    @ColumnInfo(name = COLUMN_TASK_DONE)
    val done: Boolean,

    @ColumnInfo(name = COLUMN_TASK_DATE)
    val deadline: Long? = null,

    @ColumnInfo(name = COLUMN_TASK_CREATED)
    val created: Long,

    @ColumnInfo(name = COLUMN_TASK_UPDATED)
    val updated: Long? = null,
)