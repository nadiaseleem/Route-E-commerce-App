package com.example.data.authenticationApi

import com.example.data.modelResponse.user.loginResponse.UserLoginResponse
import com.example.data.modelResponse.user.registerResponse.UserRegisterResponse
import com.example.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationWebServices {
    @POST("api/v1/auth/signup/")
    suspend fun registerUser(@Body user: User): UserRegisterResponse

    @POST("api/v1/auth/signin/")
    suspend fun loginUser(@Body user: User): UserLoginResponse
}