package com.example.routee_commerce.userRepository

import com.example.routee_commerce.data.api.model.User
import com.example.routee_commerce.data.api.model.UserResponse

interface UserRepository {
    suspend fun registerUser(user: User): UserResponse
}