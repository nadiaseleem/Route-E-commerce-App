package com.example.data.modelResponse

import com.example.data.modelResponse.user.registerResponse.Errors
import com.google.gson.annotations.SerializedName

open class BaseResponse<T>(
    @field:SerializedName("StatusMsg")
    val statusMsg: String? = null,
    @field:SerializedName("message")
    val message: String? = null,
    @field:SerializedName("data")
    val data: T? = null,
    @field:SerializedName("errors")
    val errors: Errors? = null
)