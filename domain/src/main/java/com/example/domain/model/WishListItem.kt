package com.example.domain.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class WishListItem(
	val sold: Int? = null,
	val images: List<String?>? = null,
	val quantity: Int? = null,
	val imageCover: String? = null,
	val description: String? = null,
	val title: String? = null,
	val ratingsQuantity: Int? = null,
	val ratingsAverage: Double? = null,
	val createdAt: String? = null,
	val price: Int? = null,
	val v: Int? = null,
	val id: String? = null,
	val subcategory: List<SubcategoryItem?>? = null,
	val category: Category? = null,
	val brand: Brand? = null,
	val slug: String? = null,
	val updatedAt: String? = null
) : Parcelable