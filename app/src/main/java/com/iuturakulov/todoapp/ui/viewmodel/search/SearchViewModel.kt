package com.iuturakulov.todoapp.ui.viewmodel.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.iuturakulov.todoapp.data.repository.TodoItemsRepository
import com.iuturakulov.todoapp.extensions.Result.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TEXT_ENTERED_DEBOUNCE_MILLIS = 500L

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val tasksRepository: TodoItemsRepository
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")

    private val _searchResult = MutableStateFlow<SearchResult>(SearchResult.EmptyQuery)
    val searchResult: LiveData<SearchResult>
        get() = _searchResult
            .asLiveData(viewModelScope.coroutineContext)

    init {
        viewModelScope.launch {
            queryFlow
                .sample(TEXT_ENTERED_DEBOUNCE_MILLIS)
                .onEach { _searchResult.value = SearchResult.Loading }
                .mapLatest(::handleQuery)
                .collect { state -> _searchResult.value = state }
        }
    }

    fun onNewQuery(query: String) {
        queryFlow.value = query
    }

    private suspend fun handleQuery(query: String): SearchResult {
        return if (query.isEmpty()) {
            SearchResult.EmptyQuery
        } else {
            handleSearchTask(query)
        }
    }

    private suspend fun handleSearchTask(query: String): SearchResult {
        return when(val tasksResult = tasksRepository.searchTasks(query)) {
            is Error -> SearchResult.ErrorResult(IllegalArgumentException("Search tasks from server database error!"))
            is Success -> if (tasksResult.result.isEmpty()) SearchResult.EmptyResult else
                SearchResult.SuccessResult(tasksResult.result)
        }
    }
}