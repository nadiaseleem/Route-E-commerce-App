package com.example.domain.usecase

import com.example.domain.common.ResultWrapper
import com.example.domain.model.User
import com.example.domain.repositories.user.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend fun invoke(user: User): Flow<ResultWrapper<String?>> {
        return repository.loginUser(user)
    }
}