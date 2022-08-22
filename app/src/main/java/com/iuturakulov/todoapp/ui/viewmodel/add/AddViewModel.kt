package com.iuturakulov.todoapp.ui.viewmodel.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iuturakulov.todoapp.data.dao.TaskPriorities
import com.iuturakulov.todoapp.data.repository.TodoItemsRepository
import com.iuturakulov.todoapp.extensions.*
import com.iuturakulov.todoapp.model.TodoItem
import com.iuturakulov.todoapp.model.TodoItemId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.System.currentTimeMillis
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class AddViewModel @Inject constructor(
    private val tasksRepository: TodoItemsRepository
) : ViewModel() {
    private val _addState = MutableLiveData<AddState>(AddState.Initial)
    val addState: LiveData<AddState> get() = _addState

    private val _titles = MutableStateFlow<List<TodoItemId>>(emptyList())

    var date: Calendar = Calendar.getInstance()

    init {
        getAllTitlesAndIds()
        date.add(Calendar.DAY_OF_MONTH, 1)
    }

    fun add(
        title: String,
        description: String,
        category: TaskPriorities,
        date: Long = this.date.timeInMillis
    ) {
        _addState.value = AddState.Loading

        val validatedTitle = TodoItem.TitleTask.validate(title)
        val validatedDescription = TodoItem.DescriptionTask.validate(description)

        when {
            (validatedTitle == null) -> {
                _addState.value = AddState.InputError.Title.Empty
            }
            (checkTitleNotUnique(validatedTitle)) -> {
                _addState.value = AddState.InputError.Title.NotUnique
            }
            (validatedDescription == null) -> {
                _addState.value = AddState.InputError.Description
            }
            (isValidDate(this.date.timeInMillis).not()) -> {
                _addState.value = AddState.InputError.Date
            }
            else -> {
                executeAdd(title, description, category, date)
            }
        }
    }

    private fun executeAdd(
        title: String,
        description: String,
        category: TaskPriorities,
        date: Long
    ) {
        val task = TodoItem(
            id = Random.nextLong(),
            title = TodoItem.TitleTask(title),
            description = TodoItem.DescriptionTask(description),
            taskPriority = category,
            isDone = false,
            created = date
        )
        Timber.i("id=${task.id}")
        viewModelScope.launch {
            handleAdd(task)
        }
    }

    private fun checkTitleNotUnique(title: TodoItem.TitleTask): Boolean {
        val titles = _titles.value
        titles.forEach {
            if (title.value == it.title.value) {
                return true
            }
        }
        return false
    }

    private fun getAllTitlesAndIds() {
        viewModelScope.launch {
            when (val result = tasksRepository.getAllTitlesAndIds()) {
                is Result.Error -> Unit
                is Result.Success -> {
                    _titles.value = result.result
                }
            }
        }
    }

    private suspend fun handleAdd(task: TodoItem) {
        when (val result = tasksRepository.insertTask(task)) {
            is Result.Error -> _addState.value = AddState.Error
            is Result.Success -> {
                Timber.i("Add result=$result")
                _addState.value = AddState.Success(task.id)
            }
        }
    }

    private fun isValidDate(date: Long) =
        date > currentTimeMillis()

}