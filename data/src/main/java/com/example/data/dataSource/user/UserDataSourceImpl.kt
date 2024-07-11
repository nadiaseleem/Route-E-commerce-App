package com.example.data.dataSource.user

import com.example.data.api.WebServices
import com.example.data.dataSourceContract.user.UserDataSource
import com.example.data.safeApiCall
import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    UserDataSource {
    override suspend fun registerUser(user: User): Flow<ResultWrapper<String?>> {

        return safeApiCall { webServices.registerUser(user).token }
    }

}