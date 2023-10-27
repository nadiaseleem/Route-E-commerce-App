package com.example.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val password: String? = null,
    val rePassword: String? = null,
    val token: String? = null
) : Parcelable