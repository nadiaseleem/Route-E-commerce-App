package com.example.routee_commerce.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(

    @field:SerializedName("statusMsg")
    val statusMsg: String? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("errors")
    val errors: Errors? = null,

    @field:SerializedName("token")
    val token: String? = null
) : Parcelable

@Parcelize
data class Errors(

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("param")
    val param: String? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("value")
    val value: String? = null
) : Parcelable

