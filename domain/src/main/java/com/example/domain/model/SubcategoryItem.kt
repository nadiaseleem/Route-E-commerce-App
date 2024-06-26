package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class SubcategoryItem(
	val name: String? = null,
	val id: String? = null,
	val category: String? = null,
	val slug: String? = null
) : Parcelable