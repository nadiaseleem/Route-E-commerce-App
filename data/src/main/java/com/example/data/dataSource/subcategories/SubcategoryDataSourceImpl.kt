package com.example.data.dataSource.subcategories

import com.example.data.api.CategoriesWebServices
import com.example.data.dataSourceContract.subcategory.SubcategoryDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Subcategory
import javax.inject.Inject

class SubcategoryDataSourceImpl @Inject constructor(private val categoriesWebServices: CategoriesWebServices) :
    SubcategoryDataSource {
    override suspend fun getSubcategories(category: String): ResultWrapper<List<Subcategory?>?> {
        return safeApiCall {
            categoriesWebServices.getSubcategories(category).data?.map {
                it?.toSubCategory()
            }
        }
    }

}