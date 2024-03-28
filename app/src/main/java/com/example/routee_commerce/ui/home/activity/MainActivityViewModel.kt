package com.example.routee_commerce.ui.home.activity

import androidx.lifecycle.ViewModel
import com.example.routee_commerce.utils.SingleLiveEvent

class MainActivityViewModel : ViewModel(), MainActivityContract.ViewModel {
    private val _events = SingleLiveEvent<MainActivityContract.Event>()

    override val events: SingleLiveEvent<MainActivityContract.Event>
        get() = _events

    override fun invokeAction(action: MainActivityContract.Action) {
        when (action) {
            is MainActivityContract.Action.searchForProducts -> {
                _events.postValue(MainActivityContract.Event.NavigateToProductList(action.searchKeyWord))
            }
        }
    }
}
