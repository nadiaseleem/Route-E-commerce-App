package com.example.routee_commerce.ui.home.activity

import com.example.routee_commerce.utils.SingleLiveEvent

class MainActivityContract {
    interface ViewModel {
        val events: SingleLiveEvent<Event>
        fun invokeAction(action: Action)
    }

    sealed class Action {
        class searchForProducts(val searchKeyWord: String) : Action()
    }

    sealed class Event {
        class NavigateToProductList(val searchKeyWord: String) : Event()
    }
}
