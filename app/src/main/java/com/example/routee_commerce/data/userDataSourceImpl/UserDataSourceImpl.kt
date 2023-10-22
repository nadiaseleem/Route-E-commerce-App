package com.example.routee_commerce.data.userDataSourceImpl

import com.example.routee_commerce.data.api.WebServices
import com.example.routee_commerce.data.api.model.User
import com.example.routee_commerce.data.api.model.UserResponse
import com.example.routee_commerce.data.userDataSource.UserDataSource
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val webServices: WebServices) :
    UserDataSource {
    override suspend fun registerUser(user: User): UserResponse {
        return webServices.registerUser(user)
    }

}