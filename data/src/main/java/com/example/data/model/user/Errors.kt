package com.example.data.model.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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
