package com.example.routee_commerce.ui.home.fragments.productList

import androidx.lifecycle.LiveData
import com.example.domain.model.Product
import com.example.routee_commerce.utils.SingleLiveEvent

class ProductListContract {
    interface ViewModel {
        val productsListState: LiveData<ProductsListState>
        val events: SingleLiveEvent<Event>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        class SearchForProducts(val searchKeyWord: String) : Action()
        class LoadProducts(val products: List<Product>) : Action()
        class ProductClicked(val product: Product) : Action()
    }

    sealed class ProductsListState {
        class Loading(val message: String? = null) : ProductsListState()
        class Error(val message: String) : ProductsListState()
        class Success(val products: List<Product?>) : ProductsListState()
    }

    sealed class Event {
        class NavigateToProductDetails(val product: Product) : Event()
    }
}
