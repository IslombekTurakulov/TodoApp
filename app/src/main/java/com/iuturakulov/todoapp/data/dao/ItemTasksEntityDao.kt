package com.iuturakulov.todoapp.data.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.iuturakulov.todoapp.data.TaskPriority
import com.iuturakulov.todoapp.model.TimeTodo
import com.iuturakulov.todoapp.model.TodoItem

@Entity(
    tableName = ItemTasksEntityDao.TABLE_NAME,
    indices = [Index(
        value = [ItemTasksEntityDao.COLUMN_TASK_TITLE, ItemTasksEntityDao.COLUMN_TASK_PRIORITY],
        unique = true
    )]
)
class ItemTasksEntityDao(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_TASK_ID)
    val id: Long,
    @ColumnInfo(name = COLUMN_TASK_TITLE)
    val title: TodoItem.TitleTask,
    @ColumnInfo(name = COLUMN_TASK_DESCRIPTION)
    val description: TodoItem.DescriptionTask,
    @ColumnInfo(name = COLUMN_TASK_PRIORITY)
    val priority: TaskPriority,
    @ColumnInfo(name = COLUMN_TASK_DONE)
    val isDone: Boolean,
    @ColumnInfo(name = COLUMN_TASK_DATE)
    val date: TimeTodo
) {
    companion object {
        const val TABLE_NAME = "item_tasks"
        const val COLUMN_TASK_TITLE = "title"
        const val COLUMN_TASK_DESCRIPTION = "description"
        const val COLUMN_TASK_PRIORITY = "priority"
        const val COLUMN_TASK_ID = "task_id"
        const val COLUMN_TASK_DONE = "task_done"
        const val COLUMN_TASK_DATE = "task_date"
    }
}