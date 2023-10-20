package com.example.routee_commerce.ui.home.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecases.GetCategoriesUseCase
import com.example.routee_commerce.utils.EventWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoryUseCase: GetCategoriesUseCase
) : ViewModel(), HomeFragmentContract.ViewModel {
    private val _states = MutableLiveData<HomeFragmentContract.State>()
    override val states: LiveData<HomeFragmentContract.State>
        get() = _states

    private val _events = MutableLiveData<EventWrapper<HomeFragmentContract.Event>>()
    override val events: MutableLiveData<EventWrapper<HomeFragmentContract.Event>>
        get() = _events

    override fun invokeAction(action: HomeFragmentContract.Action) {
        when (action) {
            is HomeFragmentContract.Action.LoadCategories -> {
                loadCategories()
            }

            is HomeFragmentContract.Action.CategoryClicked -> {
                _events.postValue(
                    EventWrapper(
                        HomeFragmentContract.Event.NavigateToSubCategories(
                            action.position,
                            action.category
                        )
                    )
                )
            }

        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _states.postValue(HomeFragmentContract.State.Loading())
            when (val result = categoryUseCase.invoke()) {
                is ResultWrapper.Error -> {
                    _states.postValue(HomeFragmentContract.State.Error(message = result.error.localizedMessage))

                }

                ResultWrapper.Loading -> {
                    // _states.postValue(HomeFragmentContract.State.Loading(message = "Loading Categories"))

                }

                is ResultWrapper.ServerError -> {
                    _states.postValue(HomeFragmentContract.State.Error(message = result.error.localizedMessage))

                }

                is ResultWrapper.Success -> {
                    _states.postValue(HomeFragmentContract.State.Success((result.data ?: listOf())))

                }
            }


        }
    }

}