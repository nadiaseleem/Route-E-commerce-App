package com.example.data.dataSourceContract

import com.example.domain.common.ResultWrapper
import com.example.domain.model.WishListItem
import kotlinx.coroutines.flow.Flow

interface WishListDataSource {
    suspend fun getWishListResponse():Flow<ResultWrapper<List<WishListItem?>?>>
} 