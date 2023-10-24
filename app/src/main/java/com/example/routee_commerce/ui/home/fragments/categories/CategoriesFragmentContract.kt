package com.example.routee_commerce.ui.home.fragments.categories

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.Subcategory
import com.example.routee_commerce.utils.SingleLiveEvent

interface CategoriesFragmentContract {

    interface ViewModel {
        val categoriesStates: LiveData<State<Category>>
        val subcategoriesStates: LiveData<State<Subcategory>>

        val events: SingleLiveEvent<Event>
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
        class Success<T>(val data: List<T?>) : State<T>()
    }

    sealed class Event {
        class ShowSubCategories(val position: Int, val category: Category) : Event()
    }


}