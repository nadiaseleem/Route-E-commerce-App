package com.example.data

import com.example.data.model.BaseResponse
import com.example.domain.common.ResultWrapper
import com.example.domain.exceptions.ParsingException
import com.example.domain.exceptions.ServerException
import com.example.domain.exceptions.ServerTimeOutException
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

suspend fun <T> SafeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
    try {
        val result = apiCall.invoke()
        return ResultWrapper.Success(result)
    } catch (e: Exception) {
        when (e) {
            is TimeoutException -> {
                return ResultWrapper.Error(ServerTimeOutException(e))
            }

            is IOException -> {
                return ResultWrapper.Error(ServerTimeOutException(e))
            }

            is HttpException -> {
                val body = e.response()?.errorBody()?.string()
                val response = Gson().fromJson(body, BaseResponse::class.java)
                return ResultWrapper.ServerError(
                    ServerException(
                        response.statusMsg ?: "",
                        response.message ?: "",
                        e.code()
                    )
                )
            }

            is JsonSyntaxException -> {
                return ResultWrapper.Error(ParsingException(e))
            }

            else -> return ResultWrapper.Error(e)

        }
    }
}