package com.example.routee_commerce.data.userRepositoryImpl

import com.example.routee_commerce.data.api.model.User
import com.example.routee_commerce.data.api.model.UserResponse
import com.example.routee_commerce.data.userDataSource.UserDataSource
import com.example.routee_commerce.userRepository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override suspend fun registerUser(user: User): UserResponse {
        return userDataSource.registerUser(user)
    }
}