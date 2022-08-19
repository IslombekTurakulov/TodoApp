package com.iuturakulov.todoapp.data.dao

import android.content.Context
import androidx.annotation.StringRes
import androidx.room.Entity
import com.iuturakulov.todoapp.R

@Entity
enum class
TaskPriorities(@StringRes val value: Int) {
    LOW(R.string.priority_low),
    EMPTY(R.string.priority_empty),
    HIGH(R.string.priority_high);


    companion object {
        fun String.toPriority(context: Context): TaskPriorities {
            return when (this) {
                context.getString(LOW.value) -> LOW
                context.getString(HIGH.value) -> HIGH
                else -> EMPTY
            }
        }
    }
}