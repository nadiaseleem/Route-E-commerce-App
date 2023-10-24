package com.example.routee_commerce.ui.home.fragments.home

import com.example.routee_commerce.ui.home.fragments.home.adapters.CategoriesAdapter
import com.example.routee_commerce.ui.home.fragments.home.adapters.ProductsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object HomeFragmentModule {

    @Provides
    fun provideCategoriesAdapter() = CategoriesAdapter()

    @Provides
    fun provideProductsAdapter() = ProductsAdapter()
}