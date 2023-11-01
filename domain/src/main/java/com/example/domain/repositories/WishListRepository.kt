package com.example.domain.repositories

import com.example.domain.Constants
import com.example.domain.common.ResultWrapper
import com.example.domain.model.WishListItem
import kotlinx.coroutines.flow.Flow


interface WishListRepository {
    suspend fun getWishList(token:String =Constants.token):Flow<ResultWrapper<List<WishListItem?>?>
>}