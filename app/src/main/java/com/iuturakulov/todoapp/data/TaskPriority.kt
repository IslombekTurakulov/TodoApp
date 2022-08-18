package com.iuturakulov.todoapp.data

import android.content.Context
import androidx.annotation.StringRes
import com.iuturakulov.todoapp.R

enum class
TaskPriority(@StringRes val title: Int) {
    LOW(R.string.priority_low),
    EMPTY(R.string.priority_empty),
    HIGH(R.string.priority_high);


    companion object {
        fun String.toPriority(context: Context): TaskPriority {
            return when (this) {
                context.getString(LOW.title) -> LOW
                context.getString(HIGH.title) -> HIGH
                else -> EMPTY
            }
        }
    }
}