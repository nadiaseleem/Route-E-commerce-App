package com.example.data.dataSourceContract.user

import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    suspend fun registerUser(user: User): Flow<ResultWrapper<String?>>
}