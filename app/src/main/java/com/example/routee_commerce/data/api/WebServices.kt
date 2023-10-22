package com.example.routee_commerce.data.api

import com.example.routee_commerce.data.api.model.User
import com.example.routee_commerce.data.api.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface WebServices {
    @POST("api/v1/auth/signup/")
    suspend fun registerUser(@Body user: User): UserResponse
}