package com.example.data.dataSource.category

import com.example.data.api.CategoriesWebServices
import com.example.data.dataSourceContract.category.CategoryDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(private val categoriesWebServices: CategoriesWebServices) :
    CategoryDataSource {
    override suspend fun getCategories(): ResultWrapper<List<Category?>?> {
        return safeApiCall {
            categoriesWebServices.getCategories().data?.map {
                it?.toCategory()
            }
        }
    }
}