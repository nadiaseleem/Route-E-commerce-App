package com.example.routee_commerce.data.userDataSource

import com.example.routee_commerce.data.api.model.User
import com.example.routee_commerce.data.api.model.UserResponse

interface UserDataSource {
    suspend fun registerUser(user: User): UserResponse
}