package com.example.data.repository.products

import com.example.data.dataSourceContract.product.ProductDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import com.example.domain.repositories.products.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val dataSource: ProductDataSource) :
    ProductRepository {
    override suspend fun getMostSellingProducts(
        limit: Int,
        sort: String
    ): ResultWrapper<List<Product?>?> {
        return dataSource.getMostSellingProducts(limit, sort)
    }

    override suspend fun getProducts(categoryId: String?): ResultWrapper<List<Product?>?> {
        return dataSource.getProducts(categoryId)
    }
}