package com.example.routee_commerce.ui.home.fragments.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.domain.model.Category
import com.example.domain.model.Subcategory
import com.example.routee_commerce.utils.EventWrapper

interface CategoriesFragmentContract {

    interface ViewModel {
        val categoriesStates: LiveData<State<Category>>
        val subcategoriesStates: LiveData<State<Subcategory>>

        val events: MutableLiveData<EventWrapper<Event>>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        object LoadCategories : Action()
        class CategoryClicked(val position: Int, val category: Category) : Action()
        class LoadSubCategories(val category: Category) : Action()
    }

    sealed class State<out T> {
        class Loading(val message: String? = null) : State<Nothing>()
        class Error(val message: String) : State<Nothing>()
        class Success<T>(val categories: List<T?>) : State<T>()
    }

    sealed class Event {
        class ShowSubCategories(val position: Int, val category: Category) : Event()
    }


}