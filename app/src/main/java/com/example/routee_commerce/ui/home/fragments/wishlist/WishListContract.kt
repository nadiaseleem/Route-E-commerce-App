package com.example.routee_commerce.ui.home.fragments.wishlist

import androidx.lifecycle.LiveData
import com.example.domain.model.WishListItem
import kotlinx.coroutines.flow.StateFlow

class WishListContract {
    interface ViewModel{
        val state:StateFlow<State>
        val event:LiveData<Event>
        fun invokeAction(action:Action)
    }
    sealed class Action{
        object LoadWishList : Action()
    }
    sealed class State{
        class Error(val message:String):State()
        class Success(var wishListItems:List<WishListItem?>?):State()
        class Loading(val message:String):State()
    }
    sealed class Event{

    }

}