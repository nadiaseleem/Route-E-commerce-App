package com.example.data.dataSource.category

import com.example.data.dataSourceContract.category.CategoryDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.news.data.api.WebServices
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    CategoryDataSource {
    override suspend fun getCategories(): ResultWrapper<List<Category?>?> {
        return safeApiCall {
            webServices.getCategories().data?.map {
                it?.toCategory()
            }
        }
    }
}