package com.example.data.repository.subcategories

import com.example.data.dataSourceContract.subcategory.SubcategoryDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Subcategory
import com.example.domain.repositories.subcategories.SubcategoryRepository
import javax.inject.Inject

class SubcategoriesRepositoryImp @Inject constructor(private val dataSource: SubcategoryDataSource) :
    SubcategoryRepository {
    override suspend fun getSubcategories(category: String): ResultWrapper<List<Subcategory?>?> {
        return dataSource.getSubcategories(category)
    }
}