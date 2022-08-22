package com.iuturakulov.todoapp.data.dao

import androidx.room.*
import androidx.room.Dao
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_DESCRIPTION
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_ID
import com.iuturakulov.todoapp.extensions.Constants.COLUMN_TASK_TITLE
import com.iuturakulov.todoapp.extensions.Constants.TABLE_NAME

@Dao
interface TasksDao {

    @Query(
        value = """
                SELECT *
                FROM $TABLE_NAME
                WHERE ${COLUMN_TASK_ID}=:id
            """
    )
    suspend fun getById(id: Long): ItemTasksEntity?

    @Query(
        """
        SELECT *
        FROM $TABLE_NAME
        WHERE $COLUMN_TASK_TITLE LIKE :query OR
        $COLUMN_TASK_DESCRIPTION LIKE :query
    """
    )
    suspend fun searchTasks(query: String): List<ItemTasksEntity>?

    @Query(
        """
        SELECT *
        FROM $TABLE_NAME
    """
    )
    suspend fun getAll(): List<ItemTasksEntity?>?

    @Query(
        """
        SELECT ${COLUMN_TASK_ID}, $COLUMN_TASK_TITLE
        FROM $TABLE_NAME
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
    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(task: ItemTasksEntity): Int
}