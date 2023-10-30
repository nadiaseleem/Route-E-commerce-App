package com.example.domain.repositories.user

import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun registerUser(user: User): Flow<ResultWrapper<String?>>
    suspend fun loginUser(user: User): Flow<ResultWrapper<String?>>
}