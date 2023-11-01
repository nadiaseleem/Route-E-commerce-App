package com.example.data.model.wishlistResponse

import com.example.data.model.BaseResponse
import com.example.domain.model.WishListItem
import com.google.gson.annotations.SerializedName

data class WishListResponse(
	@field:SerializedName("count")
	val count: Int? = null,
	@field:SerializedName("status")
	val status: String? = null

):BaseResponse<List<WishListItem?>? >()