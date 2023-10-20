package com.example.routee_commerce.ui.home.fragments.wishlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WishListItem
import com.example.domain.usecase.getWishListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WishListViewModel @Inject constructor(
    private val getWishListUseCase: getWishListUseCase
):ViewModel() {
    val wishListLiveData = MutableLiveData<List<WishListItem?>?>()
    fun getWishList(){
        viewModelScope.launch {
            val list = getWishListUseCase.invoke()
            wishListLiveData.postValue(list)
        }
    }
}