package com.iuturakulov.todoapp.extensions

import com.iuturakulov.todoapp.data.TaskPriorities
import com.iuturakulov.todoapp.data.dao.ItemTasksEntityDao
import com.iuturakulov.todoapp.data.dao.TaskTitleEntityDao
import com.iuturakulov.todoapp.model.TaskPriority
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import timber.log.Timber

internal fun ItemTasksEntityDao.toModel(): TodoItem =
    TodoItem(
        id = this.id,
        title = TodoItem.TitleTask(this.title.title),
        description = TodoItem.DescriptionTask(this.description.description),
        taskPriority = this.priority,
        isDone = this.isDone,
        timeTodo = this.date
    )

internal fun TodoItem.toEntity(): ItemTasksEntityDao =
    ItemTasksEntityDao(
        id = this.id,
        title = TodoItem.TitleTask(this.title.title),
        description = TodoItem.DescriptionTask(this.description.description),
        priority = this.taskPriority,
        isDone = this.isDone,
        date = this.timeTodo
    )

internal fun TaskTitleEntityDao.toModel(): TodoItemId =
    TodoItemId(
        id = this.id,
        title = TodoItem.TitleTask(this.title)
    )

internal fun List<ItemTasksEntityDao?>.toCategories(): List<TaskPriority> {
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