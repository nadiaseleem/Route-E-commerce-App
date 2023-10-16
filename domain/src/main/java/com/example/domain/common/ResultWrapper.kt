package com.example.domain.common

import com.example.domain.exceptions.ServerException

sealed class ResultWrapper<out T> {
    object Loading : ResultWrapper<Nothing>()
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class ServerError(val error: ServerException) : ResultWrapper<Nothing>()
    data class Error(val error: Exception) : ResultWrapper<Nothing>()

}