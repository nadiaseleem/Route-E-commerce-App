package com.example.routee_commerce.ui.home.fragments.categories

import com.example.routee_commerce.ui.home.fragments.categories.adapters.CategoriesAdapter
import com.example.routee_commerce.ui.home.fragments.categories.adapters.SubcategoriesAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object CategoriesFragmentModule {

    @Provides
    fun provideCategoriesAdapter(): CategoriesAdapter = CategoriesAdapter()

    @Provides
    fun provideSubcategoriesAdapter(): SubcategoriesAdapter = SubcategoriesAdapter()

}