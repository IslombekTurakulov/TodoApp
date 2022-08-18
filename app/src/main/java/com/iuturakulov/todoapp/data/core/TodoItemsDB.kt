package com.iuturakulov.todoapp.data.core

import com.iuturakulov.todoapp.data.dao.ItemTasksEntityDao
import com.iuturakulov.todoapp.data.dao.TasksDao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    entities = [
        ItemTasksEntityDao::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TodoItemsDB : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        fun create(@ApplicationContext appContext: Context): TodoItemsDB =
            Room.databaseBuilder(
                appContext,
                TodoItemsDB::class.java,
                "tasks_database"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}