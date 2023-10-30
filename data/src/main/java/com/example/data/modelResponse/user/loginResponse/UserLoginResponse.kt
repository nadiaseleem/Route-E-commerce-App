package com.example.data.modelResponse.user.loginResponse

import android.os.Parcelable
import com.example.domain.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLoginResponse(

    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("token")
    val token: String? = null
) : Parcelable