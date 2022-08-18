package com.iuturakulov.todoapp.data.dao

import androidx.room.*
import androidx.room.Dao

@Dao
interface TasksDao {

    @Query(
        value = """
                SELECT *
                FROM ${ItemTasksEntityDao.TABLE_NAME}
                WHERE ${ItemTasksEntityDao.COLUMN_TASK_ID}=:id
            """
    )
    suspend fun getById(id: Long): ItemTasksEntityDao?

    @Query(
        """
        SELECT *
        FROM ${ItemTasksEntityDao.TABLE_NAME}
        WHERE ${ItemTasksEntityDao.COLUMN_TASK_TITLE} LIKE :query OR
        ${ItemTasksEntityDao.COLUMN_TASK_DESCRIPTION} LIKE :query
    """
    )
    suspend fun searchTasks(query: String): List<ItemTasksEntityDao>?

    @Query(
        """
        SELECT *
        FROM ${ItemTasksEntityDao.TABLE_NAME}
    """
    )
    suspend fun getAll(): List<ItemTasksEntityDao?>?

    @Query(
        """
        SELECT ${ItemTasksEntityDao.COLUMN_TASK_ID}, ${ItemTasksEntityDao.COLUMN_TASK_TITLE}
        FROM ${ItemTasksEntityDao.TABLE_NAME}
    """
    )
    suspend fun getAllTitlesAndIds(): List<TaskTitleEntityDao>?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tasks: List<ItemTasksEntityDao>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: ItemTasksEntityDao): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(task: ItemTasksEntityDao): Int

    @Transaction
    @Query("DELETE FROM ${ItemTasksEntityDao.TABLE_NAME}")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(task: ItemTasksEntityDao): Int
}