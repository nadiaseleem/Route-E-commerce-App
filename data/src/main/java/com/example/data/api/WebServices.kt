package com.example.news.data.api

import com.example.data.model.category.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WebServices {
    @GET("/api/v1/categories")
    suspend fun getCategories(
        @Query("page") page: Int = 1
    ): CategoryResponse

}