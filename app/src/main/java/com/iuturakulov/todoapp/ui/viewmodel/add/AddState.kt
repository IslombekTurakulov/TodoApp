package com.iuturakulov.todoapp.ui.viewmodel.add

sealed class AddState {
    object Initial : AddState()

    object Loading : AddState()

    sealed class InputError : AddState() {

        sealed class Title : InputError() {

            object Empty : Title()

            object NotUnique : Title()
        }

        object Description : InputError()

        object Date : InputError()
    }

    object Error : AddState()

    data class Success(val taskId: Long) : AddState()
}
