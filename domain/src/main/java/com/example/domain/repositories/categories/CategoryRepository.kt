package com.example.domain.repositories.categories

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category

interface CategoryRepository {
    suspend fun getCategories(page: Int = 1): ResultWrapper<List<Category?>?>
}