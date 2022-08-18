package com.iuturakulov.todoapp.ui.viewmodel.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.iuturakulov.todoapp.data.repository.TodoItemsRepository
import com.iuturakulov.todoapp.extensions.Result
import com.iuturakulov.todoapp.model.TodoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val tasksRepository: TodoItemsRepository
) : ViewModel() {

    var currentList = emptyList<TodoItem>()

    private val _filterState = MutableStateFlow(FilterTasks.ALL)
    val filterState: LiveData<FilterTasks>
        get() = _filterState
            .asLiveData(viewModelScope.coroutineContext)

    private val _tasksResult = MutableStateFlow<TasksResult>(TasksResult.EmptyResult)
    val tasksResult: LiveData<TasksResult>
        get() = _tasksResult
            .asLiveData(viewModelScope.coroutineContext)

    fun fetchData() {
        viewModelScope.launch {
            _tasksResult.value = TasksResult.Loading
            _tasksResult.value = handleTasks()
        }
    }

    private suspend fun handleTasks(): TasksResult {
        return when (val tasks = tasksRepository.getAllTasks()) {
            is Result.Error -> TasksResult.ErrorResult(IllegalArgumentException("Tasks not found"))
            is Result.Success -> if (tasks.result.isEmpty()) {
                TasksResult.EmptyResult
            } else {
                TasksResult.SuccessResult(tasks.result)
            }
        }
    }

    fun deleteTask(task: TodoItem) {
        viewModelScope.launch {
            tasksRepository.deleteTask(task)
        }
    }

    fun addTask(task: TodoItem) {
        viewModelScope.launch {
            tasksRepository.insertTask(task)
        }
    }

    fun toggleFilterState(state: FilterTasks) {
        _filterState.value = state
    }
}