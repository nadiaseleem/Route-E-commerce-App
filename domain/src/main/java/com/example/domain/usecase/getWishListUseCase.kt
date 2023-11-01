package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.WishListItem
import com.example.domain.repositories.WishListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getWishListUseCase @Inject constructor(
    private val wishListRepository: WishListRepository
) {
    suspend fun invoke():Flow<ResultWrapper<List<WishListItem?>?>>{
        return wishListRepository.getWishList()
    }
}