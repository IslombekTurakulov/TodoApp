package com.iuturakulov.todoapp.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iuturakulov.todoapp.data.dao.ItemTasksEntity
import com.iuturakulov.todoapp.data.dao.TasksDao
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    entities = [
        ItemTasksEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TodoItemsDB : RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        @Volatile
        private var INSTANCE: TodoItemsDB? = null

        fun create(@ApplicationContext appContext: Context): TodoItemsDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    TodoItemsDB::class.java,
                    "tasks_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}