package com.example.routee_commerce.ui.home.fragments.home

import androidx.lifecycle.LiveData
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.routee_commerce.utils.SingleLiveEvent

interface HomeFragmentContract {

    interface ViewModel {
        val categoriesStates: LiveData<CategoriesState>
        val productsStates: LiveData<ProductsState>

        val events: SingleLiveEvent<Event>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        object LoadCategories : Action()

        object LoadMostSellingProducts : Action()
        class CategoryClicked(val position: Int, val category: Category) : Action()

        class ProductClicked(val product: Product) : Action()
    }

    sealed class CategoriesState {
        class Loading(val message: String? = null) : CategoriesState()
        class Error(val message: String) : CategoriesState()
        class Success(val categories: List<Category?>) : CategoriesState()
    }

    sealed class ProductsState {
        class Loading(val message: String? = null) : ProductsState()
        class Error(val message: String) : ProductsState()
        class Success(val products: List<Product?>) : ProductsState()
    }


    sealed class Event {
        class NavigateToSubCategories(val position: Int, val category: Category) : Event()

        class NavigateToProductDetails(val product: Product) : Event()
    }


}