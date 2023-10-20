package com.example.data.dataSourceContract

import com.example.domain.model.WishListItem

interface WishListDataSource {
    suspend fun getWishListResponse():List<WishListItem?>?
}