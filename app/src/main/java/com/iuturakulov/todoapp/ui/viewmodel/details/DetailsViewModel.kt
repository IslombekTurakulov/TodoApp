package com.iuturakulov.todoapp.ui.viewmodel.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.iuturakulov.todoapp.data.dao.TaskPriorities
import com.iuturakulov.todoapp.data.repository.TodoItemsRepository
import com.iuturakulov.todoapp.extensions.Result.*
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val TodoItemsRepository: TodoItemsRepository
) : ViewModel() {
    var taskId: Long = -1L
    var initialTask: TodoItem? = null

    private val _taskDoneState = MutableStateFlow(false)
    val taskDoneState: LiveData<Boolean>
        get() = _taskDoneState
            .asLiveData(viewModelScope.coroutineContext)

    private val _state = MutableStateFlow<FetchDetailsState>(FetchDetailsState.Loading)
    val state: LiveData<FetchDetailsState>
        get() = _state
            .asLiveData(viewModelScope.coroutineContext)

    private val _updateState = MutableStateFlow<UpdateState>(UpdateState.Initial)
    val updateState: LiveData<UpdateState>
        get() = _updateState
            .asLiveData(viewModelScope.coroutineContext)

    private val _deleteState = MutableStateFlow<DeleteState>(DeleteState.Initial)
    val deleteState: LiveData<DeleteState>
        get() = _deleteState
            .asLiveData(viewModelScope.coroutineContext)

    private val _titles = MutableStateFlow<List<TodoItemId>>(emptyList())

    var date: Calendar = Calendar.getInstance()

    init {
        getAllTitlesAndIds()
    }

    fun fetchData() {
        viewModelScope.launch {
            _state.value = handleTask()
        }
    }

    private suspend fun handleTask(): FetchDetailsState {
        return when (val result = TodoItemsRepository.getById(taskId)) {
            is Error -> FetchDetailsState.Error(IllegalArgumentException("Task not found"))
            is Success -> {
                initialTask = result.result

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = result.result.created
                this.date = calendar

                if (initialTask != null) {
                    _taskDoneState.value = initialTask!!.isDone
                    FetchDetailsState.Result(result.result)
                } else {
                    FetchDetailsState.Error(IllegalArgumentException("Task not found"))
                }
            }
        }
    }

    fun update(
        title: String,
        description: String,
        category: TaskPriorities,
        done: Boolean = _taskDoneState.value,
        date: Long = this.date.timeInMillis
    ) {
        _updateState.value = UpdateState.Loading

        val validatedTitle = TodoItem.TitleTask.validate(title)
        val validatedDescription = TodoItem.DescriptionTask.validate(description)

        when {
            (initialTask?.isEqualTask(
                TodoItem.TitleTask(title),
                TodoItem.DescriptionTask(description),
                category,
                done,
                date
            ) == true) -> {
                _updateState.value = UpdateState.Success
            }
            (validatedTitle == null) -> {
                _updateState.value = UpdateState.InputError.Title.Empty
            }
            (checkTitleNotUnique(validatedTitle)) -> {
                _updateState.value = UpdateState.InputError.Title.NotUnique
            }
            (validatedDescription == null) -> {
                _updateState.value = UpdateState.InputError.Description
            }
            else -> {
                executeUpdate(title, description, category, done, date)
            }
        }
    }

    private fun executeUpdate(
        title: String,
        description: String,
        category: TaskPriorities,
        done: Boolean,
        date: Long
    ) {
        val task = TodoItem(
            id = taskId,
            title = TodoItem.TitleTask(title),
            description = TodoItem.DescriptionTask(description),
            taskPriority = category,
            isDone = done,
            created = date
        )
        viewModelScope.launch {
            handleUpdate(task)
        }
    }

    private fun checkTitleNotUnique(title: TodoItem.TitleTask): Boolean {
        if (_titles.value.isEmpty()) return true
        val titles = _titles.value
        titles.forEach {
            if (title.title == it.title.title && initialTask?.id != it.id) {
                return true
            }
        }
        return false
    }

    private fun getAllTitlesAndIds() {
        viewModelScope.launch {
            when (val result = TodoItemsRepository.getAllTitlesAndIds()) {
                is Error -> Unit
                is Success -> {
                    _titles.value = result.result
                }
            }
        }
    }

    private suspend fun handleUpdate(task: TodoItem) {
        Timber.i("new task=$task")
        when (val result = TodoItemsRepository.updateTask(task)) {
            is Error -> _updateState.value = UpdateState.Error
            is Success -> {
                Timber.i("Update result=$result")
                _updateState.value = UpdateState.Success
            }
        }
    }

    private fun TodoItem?.isEqualTask(
        title: TodoItem.TitleTask,
        description: TodoItem.DescriptionTask,
        category: TaskPriorities,
        done: Boolean,
        date: Long
    ): Boolean {
        when {
            this == null -> return false
            this.title.title != title.title -> return false
            this.description.description != description.description -> return false
            this.taskPriority != category -> return false
            this.isDone != done -> return false
            this.created != date -> return false
        }
        return true
    }

    fun deleteTask(context: Context) {
        viewModelScope.launch {
            when (TodoItemsRepository.deleteTask(initialTask!!)) {
                is Error -> _deleteState.value = DeleteState.Error
                is Success -> {
                    // deleteWork(context)
                    _deleteState.value = DeleteState.Success
                }
            }
        }
    }

    /* fun deleteWork(context: Context) {
         WorkManager.getInstance(context).cancelAllWorkByTag(taskId.toString())
     }*/

    fun toggleDoneState() {
        Timber.i("toggleDoneState | ${_taskDoneState.value}")
        _taskDoneState.value = !_taskDoneState.value
        Timber.i("toggleDoneState | ${_taskDoneState.value}")
    }

}
