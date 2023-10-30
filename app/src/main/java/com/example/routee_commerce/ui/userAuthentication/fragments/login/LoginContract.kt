package com.example.routee_commerce.ui.userAuthentication.fragments.login

import androidx.lifecycle.LiveData

class LoginContract {
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
        object LoginUser : Action()
        object OutSideClicked : Action()
        object RegisterClicked : Action()
    }

    sealed class Event {
        object HideKeyboard : Event()
        object NavigateToHome : Event()
        object NavigateToRegister : Event()
    }
}