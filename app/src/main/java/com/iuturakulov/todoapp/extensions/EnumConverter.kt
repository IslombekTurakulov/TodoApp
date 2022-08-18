package com.iuturakulov.todoapp.extensions

import androidx.room.TypeConverter
import com.iuturakulov.todoapp.data.dao.TaskPriorities

class EnumConverter {

    @TypeConverter
    fun fromPriority(priority: TaskPriorities): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): TaskPriorities {
        return TaskPriorities.valueOf(priority)
    }

}