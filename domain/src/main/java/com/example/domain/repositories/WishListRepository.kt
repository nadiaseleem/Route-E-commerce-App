package com.example.domain.repositories

import com.example.domain.Constants
import com.example.domain.model.WishListItem


interface WishListRepository {
    suspend fun getWishList(token:String =Constants.token):List<WishListItem?>?
}