package com.iuturakulov.todoapp.di

import com.iuturakulov.todoapp.data.datasource.TodoItemLocalDataSource
import com.iuturakulov.todoapp.data.datasource.TodoItemLocalDataSourceImpl
import com.iuturakulov.todoapp.data.repository.TodoItemsRepository
import com.iuturakulov.todoapp.data.repository.TodoItemsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindTasksLocalDataSource(
        impl: TodoItemLocalDataSourceImpl
    ): TodoItemLocalDataSource

    @Binds
    fun bindTasksRepository(
        impl: TodoItemsRepositoryImpl
    ): TodoItemsRepository
}