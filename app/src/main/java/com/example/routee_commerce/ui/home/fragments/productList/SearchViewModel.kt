package com.example.routee_commerce.ui.home.fragments.productList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecases.SearchForProductsUseCase
import com.example.routee_commerce.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchForProductsUseCase: SearchForProductsUseCase) :
    ViewModel(),
    ProductListContract.ViewModel {
    private val _productsListState = MutableLiveData<ProductListContract.ProductsListState>()

    override val productsListState: LiveData<ProductListContract.ProductsListState>
        get() = _productsListState
    private val _events = SingleLiveEvent<ProductListContract.Event>()

    override val events: SingleLiveEvent<ProductListContract.Event>
        get() = _events

    override fun invokeAction(action: ProductListContract.Action) {
        when (action) {
            is ProductListContract.Action.LoadProducts -> {

            }

            is ProductListContract.Action.ProductClicked -> {
                _events.postValue(ProductListContract.Event.NavigateToProductDetails(action.product))
            }

            is ProductListContract.Action.SearchForProducts -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _productsListState.postValue(ProductListContract.ProductsListState.Loading())
                    val result = searchForProductsUseCase.invoke(action.searchKeyWord)
                    when (result) {
                        is ResultWrapper.Error -> {
                            _productsListState.postValue(
                                ProductListContract.ProductsListState.Error(
                                    result.error.localizedMessage
                                )
                            )

                        }

                        ResultWrapper.Loading -> {
                            _productsListState.postValue(ProductListContract.ProductsListState.Loading())

                        }

                        is ResultWrapper.ServerError -> {
                            _productsListState.postValue(
                                ProductListContract.ProductsListState.Error(
                                    result.error.localizedMessage
                                )
                            )

                        }

                        is ResultWrapper.Success -> {
                            _productsListState.postValue(
                                ProductListContract.ProductsListState.Success(
                                    result.data ?: listOf()
                                )
                            )
                            Log.i("products view model @@", result.data.toString())

                        }
                    }

                }
            }

        }
    }
}
