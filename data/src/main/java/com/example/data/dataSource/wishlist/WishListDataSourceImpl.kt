package com.example.data.dataSource.wishlist

import com.example.data.safeApiCall
import com.example.data.dataSourceContract.WishListDataSource
import com.example.data.api.WebServices
import com.example.domain.common.ResultWrapper

import com.example.domain.model.WishListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishListDataSourceImpl @Inject constructor(
    private val webServices: WebServices
):WishListDataSource {
    override suspend fun getWishListResponse(): Flow<ResultWrapper<List<WishListItem?>?>> {
        return safeApiCall {
            webServices.getWishList()
        }
    }
}
