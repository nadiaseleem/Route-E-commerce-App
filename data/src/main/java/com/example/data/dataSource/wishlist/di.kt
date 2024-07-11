package com.example.data.dataSource.wishlist

import com.example.data.dataSourceContract.WishListDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class di {
    @Binds
    abstract fun provideWishListDataSource(
        wishLListDataSourceImpl: WishListDataSourceImpl
    ):WishListDataSource
}