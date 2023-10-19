package com.example.routee_commerce.ui.home.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.Category
import com.example.routee_commerce.utils.EventWrapper

interface HomeFragmentContract {

    interface ViewModel {
        val states: LiveData<State>
        val events: MutableLiveData<EventWrapper<Event>>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        object LoadCategories : Action()
        class CategoryClicked(val position: Int, val category: Category) : Action()
    }

    sealed class State {
        class Loading(val message: String) : State()
        class Error(val message: String) : State()
        class Success(val categories: List<Category?>) : State()
    }

    sealed class Event {
        class NavigateToSubCategories(val position: Int, val category: Category) : Event()
    }


}