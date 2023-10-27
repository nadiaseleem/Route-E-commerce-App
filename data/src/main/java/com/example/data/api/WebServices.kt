package com.example.data.api

import com.example.data.model.user.UserRegisterResponse
import com.example.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface WebServices {
    @POST("api/v1/auth/signup/")
    suspend fun registerUser(@Body user: User): UserRegisterResponse
}