package com.example.data.repository.wishlist

import com.example.data.dataSourceContract.WishListDataSource
import com.example.domain.model.WishListItem
import com.example.domain.repositories.WishListRepository
import javax.inject.Inject

class WishListRepositoryImpl @Inject constructor(
    private val wishListDataSource: WishListDataSource
):WishListRepository {
    override suspend fun getWishList(token: String): List<WishListItem?>? {
        return wishListDataSource.getWishListResponse()
    }
}