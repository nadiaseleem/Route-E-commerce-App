package com.example.domain.usecases

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repositories.products.ProductRepository
import javax.inject.Inject

class GetMostSellingProductsUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend fun invoke(limit: Int, sort: String): ResultWrapper<List<Product?>?> {
        return repository.getMostSellingProducts(limit, sort)

    }
}