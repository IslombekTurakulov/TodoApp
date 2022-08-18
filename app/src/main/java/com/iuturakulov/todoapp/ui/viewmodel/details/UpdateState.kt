package com.iuturakulov.todoapp.ui.viewmodel.details

sealed class UpdateState {
    object Initial : UpdateState()

    object Loading : UpdateState()

    sealed class InputError : UpdateState() {

        sealed class Title : InputError() {

            object Empty : Title()

            object NotUnique : Title()
        }

        object Description : InputError()
    }

    object Error : UpdateState()

    object Success : UpdateState()
}
