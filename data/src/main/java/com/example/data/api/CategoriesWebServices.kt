package com.example.data.api

import com.example.data.model.category.CategoryResponse
import com.example.data.model.subcategories.SubcategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface CategoriesWebServices {
    @GET("api/v1/categories")
    suspend fun getCategories(
        @Query("page") page: Int = 1
    ): CategoryResponse

    @GET("api/v1/categories/6407ea3d5bbc6e43516931df/subcategories")
    suspend fun getSubcategories(
        @Query("category") categoryId: String
    ): SubcategoryResponse
}