package com.example.domain.repositories.products

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product

interface ProductRepository {
    suspend fun getMostSellingProducts(limit: Int, sort: String): ResultWrapper<List<Product?>?>
    suspend fun getProducts(categoryId: String?): ResultWrapper<List<Product?>?>

}