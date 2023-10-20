package com.example.data.model.wishlistResponse

import com.google.gson.annotations.SerializedName

data class WishListResponse(

	@field:SerializedName("data")
	val data: List<WishListItemDTO?>? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("status")
	val status: String? = null
)