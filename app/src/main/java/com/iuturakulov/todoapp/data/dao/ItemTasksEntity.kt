package com.iuturakulov.todoapp.data.dao

import androidx.room.*
import com.iuturakulov.todoapp.extensions.EnumConverter
import com.iuturakulov.todoapp.model.TodoItem
import javax.inject.Inject

@Entity(
    tableName = ItemTasksEntity.TABLE_NAME,
    indices = [Index(
        value = [ItemTasksEntity.COLUMN_TASK_TITLE],
        unique = true
    )]
)
data class ItemTasksEntity(
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
    val isDone: Boolean,
    @ColumnInfo(name = COLUMN_TASK_DATE)
    val deadline: Long,
    @ColumnInfo(name = COLUMN_TASK_CREATED)
    val created: Long,
    @ColumnInfo(name = COLUMN_TASK_UPDATED)
    val updated: Long,
) {

    companion object {
        const val TABLE_NAME = "tasks"
        const val COLUMN_TASK_TITLE = "title"
        const val COLUMN_TASK_DESCRIPTION = "description"
        const val COLUMN_TASK_PRIORITY = "priority"
        const val COLUMN_TASK_ID = "task_id"
        const val COLUMN_TASK_DONE = "task_done"
        const val COLUMN_TASK_DATE = "task_date"
        const val COLUMN_TASK_CREATED = "task_created"
        const val COLUMN_TASK_UPDATED = "task_updated"
    }
}