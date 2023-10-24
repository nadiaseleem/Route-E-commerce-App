package com.example.routee_commerce.ui.home.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecases.GetCategoriesUseCase
import com.example.domain.usecases.GetMostSellingProductsUseCase
import com.example.routee_commerce.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: GetCategoriesUseCase,
    private val mostSellingProductsUseCase: GetMostSellingProductsUseCase
) : ViewModel(), HomeFragmentContract.ViewModel {
    private val _categoriesStates = MutableLiveData<HomeFragmentContract.CategoriesState>()
    override val categoriesStates: LiveData<HomeFragmentContract.CategoriesState>
        get() = _categoriesStates

    private val _productsStates = MutableLiveData<HomeFragmentContract.ProductsState>()
    override val productsStates: LiveData<HomeFragmentContract.ProductsState>
        get() = _productsStates

    private val _events = SingleLiveEvent<HomeFragmentContract.Event>()
    override val events: SingleLiveEvent<HomeFragmentContract.Event>
        get() = _events


    override fun invokeAction(action: HomeFragmentContract.Action) {
        when (action) {
            is HomeFragmentContract.Action.LoadCategories -> {
                loadCategories()
            }

            is HomeFragmentContract.Action.CategoryClicked -> {
                _events.postValue(
                    HomeFragmentContract.Event.NavigateToSubCategories(
                        action.position,
                        action.category
                    )
                )
            }

            HomeFragmentContract.Action.LoadMostSellingProducts -> {
                loadMostSellingProducts()
            }

            is HomeFragmentContract.Action.ProductClicked -> {
                _events.postValue(HomeFragmentContract.Event.NavigateToProductDetails(action.product))
            }
        }
    }

    private fun loadMostSellingProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _productsStates.postValue(HomeFragmentContract.ProductsState.Loading())
            val result = mostSellingProductsUseCase.invoke(10, "-sold")
            when (result) {
                is ResultWrapper.Error -> {
                    _productsStates.postValue(HomeFragmentContract.ProductsState.Error(result.error.localizedMessage))
                    Log.i(" error data@", result.error.localizedMessage)

                }

                ResultWrapper.Loading -> {
                    _productsStates.postValue(HomeFragmentContract.ProductsState.Loading())
                    Log.i("loading data@", result.toString())

                }

                is ResultWrapper.ServerError -> {
                    _productsStates.postValue(HomeFragmentContract.ProductsState.Error(result.error.localizedMessage))
                    Log.i("server error data@", result.error.localizedMessage)

                }

                is ResultWrapper.Success -> {
                    _productsStates.postValue(
                        HomeFragmentContract.ProductsState.Success(
                            result.data ?: listOf()
                        )
                    )
                    Log.i("data@", result.data.toString())

                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _categoriesStates.postValue(HomeFragmentContract.CategoriesState.Loading())
            when (val result = categoryUseCase.invoke()) {
                is ResultWrapper.Error -> {
                    _categoriesStates.postValue(HomeFragmentContract.CategoriesState.Error(message = result.error.localizedMessage))

                }

                ResultWrapper.Loading -> {
                    _categoriesStates.postValue(HomeFragmentContract.CategoriesState.Loading(message = "Loading Categories"))

                }

                is ResultWrapper.ServerError -> {
                    _categoriesStates.postValue(HomeFragmentContract.CategoriesState.Error(message = result.error.localizedMessage))

                }

                is ResultWrapper.Success -> {
                    _categoriesStates.postValue(
                        HomeFragmentContract.CategoriesState.Success(
                            (result.data ?: listOf())
                        )
                    )

                }
            }


        }
    }

}