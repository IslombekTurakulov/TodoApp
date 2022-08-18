package com.iuturakulov.todoapp.ui.viewmodel.explore

import android.content.Context
import androidx.annotation.StringRes
import com.iuturakulov.todoapp.R

enum class FilterTasks(@StringRes val value: Int) {
    ALL(R.string.explore_fragment_all_tasks),
    OVERDUE(R.string.explore_fragment_overdue_tasks);

    companion object {
        fun String.toFilterTasks(context: Context): FilterTasks =
            when (this) {
                context.getString(ALL.value) -> ALL
                context.getString(OVERDUE.value) -> OVERDUE
                else -> ALL
            }
    }
}