package com.example.data.dataSource.product

import com.example.data.api.ProductsWebServices
import com.example.data.dataSourceContract.product.ProductDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.Product
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(private val webServices: ProductsWebServices) :
    ProductDataSource {
    override suspend fun getMostSellingProducts(
        limit: Int,
        sort: String
    ): ResultWrapper<List<Product?>?> {
        return safeApiCall {
            webServices.getMostSellingProducts(limit, sort).data?.map {
                it?.toProduct()
            }
        }

    }

    override suspend fun getProducts(categoryId: String?): ResultWrapper<List<Product?>?> {
        return safeApiCall { webServices.getProducts(categoryId).data?.map { it?.toProduct() } }
    }
}