package com.example.data.api
import com.example.data.model.BaseResponse
import com.example.data.model.wishlistResponse.WishListResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query
import com.example.data.model.user.UserRegisterResponse
import com.example.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST
interface WebServices {
    @GET("api/v1/wishlist")
    @Headers("token:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjY1MzA2Mjg4MDgyMDRlZGE4NTE4NDEyMSIsIm5hbWUiOiJBaG1lZEh1c3NpbiIsInJvbGUiOiJ1c2VyIiwiaWF0IjoxNjk3Nzk3NDAwLCJleHAiOjE3MDU1NzM0MDB9.i61v0XGcRz6gs3Z2eR-hHzrDqGoCOnwnUNsdBUwC86g")
   suspend fun getWishList():WishListResponse

    @POST("api/v1/auth/signup/")
    suspend fun registerUser(@Body user: User): UserRegisterResponse
}