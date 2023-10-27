package com.example.routee_commerce.ui.userAuthentication.fragments.register

import androidx.lifecycle.LiveData

class RegisterContract {
    interface ViewModel {
        val states: LiveData<State>
        val events: LiveData<Event>
        fun invokeAction(action: Action)

    }

    sealed class State {
        class Error(val message: String) : State()
        object Success : State()
        object Loading : State()
    }

    sealed class Action {
        object RegisterUser : Action()
        object OutSideClicked : Action()
        object LoginClicked : Action()
    }

    sealed class Event {
        object HideKeyboard : Event()
        object NavigateToHome : Event()
        object NavigateToLogin : Event()
    }
}