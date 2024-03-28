package com.example.routee_commerce.ui.home.fragments.productList

import com.example.routee_commerce.ui.home.fragments.productList.adapter.ProductsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ProductListModule {

    @Provides
    fun provideProductsAdapter() = ProductsAdapter()
}
