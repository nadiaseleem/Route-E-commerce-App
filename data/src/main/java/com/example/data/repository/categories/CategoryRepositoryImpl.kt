package com.example.data.repository.categories

import com.example.data.dataSourceContract.category.CategoryDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.categories.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun getCategories(page: Int): ResultWrapper<List<Category?>?> {
        return categoryDataSource.getCategories()
    }

}