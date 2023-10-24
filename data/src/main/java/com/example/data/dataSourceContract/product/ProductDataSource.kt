package com.example.data.dataSourceContract.product

import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product

interface ProductDataSource {
    suspend fun getMostSellingProducts(limit: Int, sort: String): ResultWrapper<List<Product?>?>
    suspend fun getProducts(categoryId: String? = null): ResultWrapper<List<Product?>?>

}