package com.example.domain.usecase

import com.example.domain.model.WishListItem
import com.example.domain.repositories.WishListRepository
import javax.inject.Inject

class getWishListUseCase @Inject constructor(
    private val wishListRepository: WishListRepository
) {
    suspend fun invoke():List<WishListItem?>?{
        return wishListRepository.getWishList()
    }
}