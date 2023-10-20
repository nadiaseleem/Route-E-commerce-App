package com.example.data.dataSource.wishlist

import com.example.data.dataSourceContract.WishListDataSource
import com.example.data.api.WebServices

import com.example.domain.model.WishListItem
import javax.inject.Inject

class WishListDataSourceImpl @Inject constructor(
    private val webServices: WebServices
):WishListDataSource {
    override suspend fun getWishListResponse(): List<WishListItem?>? {
        val response = webServices.getWishList()
        return response.data?.map {
            it?.toWishList()
        }
    }
}