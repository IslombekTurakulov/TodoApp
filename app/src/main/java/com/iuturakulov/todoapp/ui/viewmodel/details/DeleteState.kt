package com.iuturakulov.todoapp.ui.viewmodel.details

sealed class DeleteState {

    object Initial : DeleteState()

    object Loading : DeleteState()

    object Success : DeleteState()

    object Error : DeleteState()
}
