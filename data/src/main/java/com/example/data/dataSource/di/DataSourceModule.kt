package com.example.data.dataSource.di

import com.example.data.dataSource.category.CategoryDataSourceImpl
import com.example.data.dataSource.subcategories.SubcategoryDataSourceImpl
import com.example.data.dataSourceContract.category.CategoryDataSource
import com.example.data.dataSourceContract.subcategory.SubcategoryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCategoriesDataSource(categoriesDataSourceImpl: CategoryDataSourceImpl):
            CategoryDataSource

    @Binds
    abstract fun bindSubcategoriesDataSource(subcategoryDataSourceImpl: SubcategoryDataSourceImpl):
            SubcategoryDataSource
}