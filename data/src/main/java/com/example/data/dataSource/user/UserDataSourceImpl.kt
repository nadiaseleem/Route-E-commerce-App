package com.example.data.dataSource.user

import com.example.data.authenticationApi.AuthenticationWebServices
import com.example.data.dataSourceContract.user.UserDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val authenticationWebServices: AuthenticationWebServices) :
    UserDataSource {
    override suspend fun registerUser(user: User): Flow<ResultWrapper<String?>> {

        return safeApiCall { authenticationWebServices.registerUser(user).token }
    }

    override suspend fun loginUser(user: User): Flow<ResultWrapper<String?>> {
        return safeApiCall { authenticationWebServices.loginUser(user).token }
    }

}