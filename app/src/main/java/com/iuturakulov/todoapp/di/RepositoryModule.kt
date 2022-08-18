package com.iuturakulov.todoapp.di

import com.iuturakulov.todoapp.data.dao.TasksDao
import com.iuturakulov.todoapp.data.repository.TodoItemsDetailRepository
import com.iuturakulov.todoapp.data.repository.TodoItemsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideTodoItemsRepository(
        archDao: TasksDao
    ): TodoItemsRepositoryImpl {
        return TodoItemsRepositoryImpl(archDao)
    }

    @Provides
    @ViewModelScoped
    fun provideTodoItemsDetailRepository(
        archInfoDao: TasksDao
    ): TodoItemsDetailRepository {
        return TodoItemsDetailRepository(archInfoDao)
    }
}