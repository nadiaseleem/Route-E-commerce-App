package com.example.data.api

import com.example.data.model.wishlistResponse.WishListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
    @GET("api/v1/wishlist")
   suspend fun getWishList(
        @Query("token")
        token:String=Constants.token
   ):WishListResponse
}