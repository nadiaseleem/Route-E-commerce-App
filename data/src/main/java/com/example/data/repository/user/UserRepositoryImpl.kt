package com.example.data.repository.user

import com.example.data.dataSourceContract.user.UserDataSource
import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import com.example.domain.repositories.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override suspend fun registerUser(user: User): Flow<ResultWrapper<String?>> {
        return userDataSource.registerUser(user)
    }
}