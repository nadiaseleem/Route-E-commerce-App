package com.example.data.repository.di

import com.example.data.repository.categories.CategoryRepositoryImpl
import com.example.data.repository.products.ProductRepositoryImpl
import com.example.data.repository.subcategories.SubcategoriesRepositoryImp
import com.example.domain.repositories.categories.CategoryRepository
import com.example.domain.repositories.products.ProductRepository
import com.example.domain.repositories.subcategories.SubcategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindSubcategoryRepository(subcategoriesRepositoryImp: SubcategoriesRepositoryImp): SubcategoryRepository

    @Binds
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}
