package com.example.routee_commerce.ui.home.fragments.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.ResultWrapper
import com.example.domain.usecase.getWishListUseCase
import com.example.routee_commerce.ui.DispatchersModule
import com.example.routee_commerce.ui.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getWishListUseCase: getWishListUseCase,
    @DispatchersModule.IODispatcher private val ioDispatcher: CoroutineDispatcher
):ViewModel(),WishListContract.ViewModel {
    private val _state = MutableStateFlow <WishListContract.State>(
        WishListContract.State.Loading("loading..")
    )
    private val _event = SingleLiveEvent<WishListContract.Event>()
    override val state: StateFlow<WishListContract.State>
        get() =_state
    override val event: LiveData<WishListContract.Event>
        get() =_event

    override fun invokeAction(action: WishListContract.Action) {
            when(action){
                is WishListContract.Action.LoadWishList-> loadWishList()
            }

    }
    private fun loadWishList(){
        viewModelScope.launch(ioDispatcher) {
                _state.emit(WishListContract.State.Loading("Loading..."))
                 getWishListUseCase.invoke().collect{response->
                    when(response){
                        is ResultWrapper.Success->{
                            _state.emit(WishListContract.State.Success(response.data))
                        }
                        is ResultWrapper.ServerError->{
                            _state.emit(WishListContract.State.Error(response.error.serverMessage))
                        }
                        is ResultWrapper.Error->{
                            _state.emit(WishListContract.State.Error(response.error.localizedMessage))
                        }
                        else -> {}
                    }
                }



        }
    }
}