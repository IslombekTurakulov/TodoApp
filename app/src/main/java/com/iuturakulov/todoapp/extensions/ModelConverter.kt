package com.iuturakulov.todoapp.extensions

import com.iuturakulov.todoapp.data.dao.TaskPriorities
import com.iuturakulov.todoapp.data.dao.ItemTasksEntity
import com.iuturakulov.todoapp.data.dao.TaskTitleEntityDao
import com.iuturakulov.todoapp.model.TaskPriority
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import timber.log.Timber

internal fun ItemTasksEntity.toModel(): TodoItem =
    TodoItem(
        id = this.id,
        title = TodoItem.TitleTask(this.title.title),
        description = TodoItem.DescriptionTask(this.description.description),
        taskPriority = this.priority,
        isDone = this.isDone,
        deadline = this.deadline,
        created = this.created,
        updated = this.updated
    )

internal fun TodoItem.toEntity(): ItemTasksEntity =
    ItemTasksEntity(
        id = this.id,
        title = TodoItem.TitleTask(this.title.title),
        description = TodoItem.DescriptionTask(this.description.description),
        priority = this.taskPriority,
        isDone = this.isDone,
        deadline = this.deadline ?: 0,
        created = this.created,
        updated = this.updated ?: 0
    )

internal fun TaskTitleEntityDao.toModel(): TodoItemId =
    TodoItemId(
        id = this.id,
        title = TodoItem.TitleTask(this.title)
    )

internal fun List<ItemTasksEntity?>.toCategories(): List<TaskPriority> {
    val highPriorityList = mutableListOf<TodoItem>()
    val lowPriorityList = mutableListOf<TodoItem>()
    val emptyPriorityList = mutableListOf<TodoItem>()
    this.forEach { task ->
        when (task?.priority) {
            TaskPriorities.HIGH -> highPriorityList.add(task.toModel())
            TaskPriorities.LOW -> lowPriorityList.add(task.toModel())
            TaskPriorities.EMPTY -> emptyPriorityList.add(task.toModel())
            null -> Timber.e("Priority $task is null")
        }
    }
    return listOf(
        TaskPriority(id = 0, title = "HIGH", tasks = highPriorityList),
        TaskPriority(id = 1, title = "LOW", tasks = lowPriorityList),
        TaskPriority(id = 2, title = "EMPTY", tasks = emptyPriorityList),
    )
}