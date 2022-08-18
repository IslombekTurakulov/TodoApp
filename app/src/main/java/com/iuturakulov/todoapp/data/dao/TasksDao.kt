package com.iuturakulov.todoapp.data.dao

import androidx.room.*
import androidx.room.Dao

@Dao
interface TasksDao {

    @Query(
        value = """
                SELECT *
                FROM ${ItemTasksEntity.TABLE_NAME}
                WHERE ${ItemTasksEntity.COLUMN_TASK_ID}=:id
            """
    )
    suspend fun getById(id: Long): ItemTasksEntity?

    @Query(
        """
        SELECT *
        FROM ${ItemTasksEntity.TABLE_NAME}
        WHERE ${ItemTasksEntity.COLUMN_TASK_TITLE} LIKE :query OR
        ${ItemTasksEntity.COLUMN_TASK_DESCRIPTION} LIKE :query
    """
    )
    suspend fun searchTasks(query: String): List<ItemTasksEntity>?

    @Query(
        """
        SELECT *
        FROM ${ItemTasksEntity.TABLE_NAME}
    """
    )
    suspend fun getAll(): List<ItemTasksEntity?>?

    @Query(
        """
        SELECT ${ItemTasksEntity.COLUMN_TASK_ID}, ${ItemTasksEntity.COLUMN_TASK_TITLE}
        FROM ${ItemTasksEntity.TABLE_NAME}
    """
    )
    suspend fun getAllTitlesAndIds(): List<TaskTitleEntityDao>?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tasks: List<ItemTasksEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: ItemTasksEntity): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: ItemTasksEntity): Int

    @Transaction
    @Query("DELETE FROM ${ItemTasksEntity.TABLE_NAME}")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(task: ItemTasksEntity): Int
}