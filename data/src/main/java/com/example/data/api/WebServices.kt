package com.example.data.api

import com.example.data.model.BaseResponse
import com.example.data.model.wishlistResponse.WishListResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query

interface WebServices {
    @GET("api/v1/wishlist")
    @Headers("token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1MzA2Mjg4MDgyMDRlZGE4NTE4NDEyMSIsIm5hbWUiOiJBaG1lZEh1c3NpbiIsInJvbGUiOiJ1c2VyIiwiaWF0IjoxNjk3Nzk3NDAwLCJleHAiOjE3MDU1NzM0MDB9.i61v0XGcRz6gs3Z2eR-hHzrDqGoCOnwnUNsdBUwC86g")
   suspend fun getWishList():WishListResponse
}