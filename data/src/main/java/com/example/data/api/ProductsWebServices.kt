package com.example.data.api

import com.example.data.model.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsWebServices {

    @GET("api/v1/products")
    suspend fun getMostSellingProducts(
        @Query("limit") limit: Int = 10,
        @Query("sort") sortBy: String = "-sold"
    ): ProductResponse

    @GET("api/v1/products")
    suspend fun getProducts(
        @Query("category[in]") category: String? = null
    ): ProductResponse


}