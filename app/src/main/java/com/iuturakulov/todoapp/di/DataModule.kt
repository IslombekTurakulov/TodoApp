package com.iuturakulov.todoapp.di

import android.content.Context
import com.iuturakulov.todoapp.data.core.TodoItemsDB
import com.iuturakulov.todoapp.data.dao.TasksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideTasksDb(
        @ApplicationContext appContext: Context
    ): TodoItemsDB = TodoItemsDB.create(appContext)

    @Provides
    fun provideTasksDao(tasksDb: TodoItemsDB): TasksDao = tasksDb.tasksDao()
}