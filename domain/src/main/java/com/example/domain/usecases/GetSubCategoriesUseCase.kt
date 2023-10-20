package com.example.domain.usecases

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Subcategory
import com.example.domain.repositories.subcategories.SubcategoryRepository
import javax.inject.Inject

class GetSubCategoriesUseCase @Inject constructor(private val subcategoryRepository: SubcategoryRepository) {

    suspend fun invoke(category: String): ResultWrapper<List<Subcategory?>?> {
        return subcategoryRepository.getSubcategories(category)

    }
}