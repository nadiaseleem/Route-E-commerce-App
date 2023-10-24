package com.example.routee_commerce.ui.home.fragments.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.model.Subcategory
import com.example.domain.usecases.GetCategoriesUseCase
import com.example.domain.usecases.GetSubCategoriesUseCase
import com.example.routee_commerce.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val categoryUseCase: GetCategoriesUseCase,
    private val subCategoryUseCase: GetSubCategoriesUseCase
) : ViewModel(), CategoriesFragmentContract.ViewModel {

    private val _categoriesStates = MutableLiveData<CategoriesFragmentContract.State<Category>>()
    override val categoriesStates: LiveData<CategoriesFragmentContract.State<Category>>
        get() = _categoriesStates


    private val _subcategoriesStates =
        MutableLiveData<CategoriesFragmentContract.State<Subcategory>>()
    override val subcategoriesStates: LiveData<CategoriesFragmentContract.State<Subcategory>>
        get() = _subcategoriesStates

    private val _events = SingleLiveEvent<CategoriesFragmentContract.Event>()
    override val events: SingleLiveEvent<CategoriesFragmentContract.Event>
        get() = _events


    override fun invokeAction(action: CategoriesFragmentContract.Action) {
        when (action) {
            is CategoriesFragmentContract.Action.LoadCategories -> {
                loadCategories()
            }

            is CategoriesFragmentContract.Action.CategoryClicked -> {
                _events.postValue(

                    CategoriesFragmentContract.Event.ShowSubCategories(
                            action.position,
                            action.category
                        )

                )
            }

            is CategoriesFragmentContract.Action.LoadSubCategories -> {
                loadSubCategories(action.category)
            }
        }
    }

    private fun loadSubCategories(category: Category) {
        viewModelScope.launch {
            _subcategoriesStates.postValue(CategoriesFragmentContract.State.Loading())
            when (val result = subCategoryUseCase.invoke(category = category.id ?: "")) {
                is ResultWrapper.Error -> {
                    _subcategoriesStates.postValue(CategoriesFragmentContract.State.Error(message = result.error.localizedMessage))

                }

                ResultWrapper.Loading -> {

                }

                is ResultWrapper.ServerError -> {
                    _subcategoriesStates.postValue(CategoriesFragmentContract.State.Error(message = result.error.localizedMessage))

                }

                is ResultWrapper.Success -> {
                    _subcategoriesStates.postValue(
                        CategoriesFragmentContract.State.Success(
                            (result.data ?: listOf())
                        )
                    )


                }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoriesStates.postValue(CategoriesFragmentContract.State.Loading())
            when (val result = categoryUseCase.invoke()) {
                is ResultWrapper.Error -> {
                    _categoriesStates.postValue(CategoriesFragmentContract.State.Error(message = result.error.localizedMessage))

                }

                ResultWrapper.Loading -> {
                    // _states.postValue(HomeFragmentContract.State.Loading(message = "Loading Categories"))

                }

                is ResultWrapper.ServerError -> {
                    _categoriesStates.postValue(CategoriesFragmentContract.State.Error(message = result.error.localizedMessage))

                }

                is ResultWrapper.Success -> {
                    _categoriesStates.postValue(
                        CategoriesFragmentContract.State.Success(
                            (result.data ?: listOf())
                        )
                    )

                }
            }


        }
    }

}