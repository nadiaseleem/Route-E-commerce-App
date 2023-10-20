package com.example.domain.repositories.subcategories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Subcategory

interface SubcategoryRepository {

    suspend fun getSubcategories(category: String): ResultWrapper<List<Subcategory?>?>
}