package com.example.data.model.user

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


