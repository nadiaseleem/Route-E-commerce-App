package com.example.data.repository.wishlist

import com.example.domain.repositories.WishListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class di {
    @Binds
    abstract fun provideWishListRepo(
        wishListRepositoryImpl: WishListRepositoryImpl
    ): WishListRepository

}