package com.example.data.dataSourceContract.subcategory

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Subcategory

interface SubcategoryDataSource {
    suspend fun getSubcategories(category: String): ResultWrapper<List<Subcategory?>?>
}