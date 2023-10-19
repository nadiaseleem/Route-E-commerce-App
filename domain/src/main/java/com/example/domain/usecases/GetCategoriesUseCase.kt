package com.example.domain.usecases

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Category
import com.example.domain.repositories.categories.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {

    suspend fun invoke(): ResultWrapper<List<Category?>?> {
        return categoryRepository.getCategories()

    }
}