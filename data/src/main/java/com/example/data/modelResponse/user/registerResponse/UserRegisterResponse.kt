package com.example.data.modelResponse.user.registerResponse

import android.os.Parcelable
import com.example.domain.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserRegisterResponse(

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("token")
    val token: String? = null
) : Parcelable


