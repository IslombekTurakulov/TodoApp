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
    var id: Long,

    @ColumnInfo(name = COLUMN_TASK_TITLE)
    var title: String,

    @ColumnInfo(name = COLUMN_TASK_DESCRIPTION)
    var description: String,

    @ColumnInfo(name = COLUMN_TASK_PRIORITY)
    var priority: TaskPriorities,

    @ColumnInfo(name = COLUMN_TASK_DONE)
    var done: Boolean,

    @ColumnInfo(name = COLUMN_TASK_DATE)
    var deadline: Long? = null,

    @ColumnInfo(name = COLUMN_TASK_CREATED)
    var created: Long,

    @ColumnInfo(name = COLUMN_TASK_UPDATED)
    var updated: Long? = null,
)