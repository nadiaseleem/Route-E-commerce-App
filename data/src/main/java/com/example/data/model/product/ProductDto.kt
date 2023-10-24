package com.example.data.model.product

import android.os.Parcelable
import com.example.domain.model.Brand
import com.example.domain.model.Category
import com.example.domain.model.Product
import com.example.domain.model.Subcategory
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDto(

	@field:SerializedName("sold")
	val sold: Int? = null,

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("imageCover")
	val imageCover: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("ratingsQuantity")
	val ratingsQuantity: Int? = null,

	@field:SerializedName("ratingsAverage")
	val ratingsAverage: Double? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("subcategory")
	val subcategory: List<Subcategory?>? = null,

	@field:SerializedName("category")
	val category: Category? = null,

	@field:SerializedName("priceAfterDiscount")
	val priceAfterDiscount: Int? = null,

	@field:SerializedName("brand")
	val brand: Brand? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
) : Parcelable {
	fun toProduct() =
		Product(
			sold = this.sold,
			images = this.images,
			quantity = this.quantity,
			imageCover = this.imageCover,
			description = this.description,
			ratingsAverage = this.ratingsAverage,
			ratingsQuantity = this.ratingsQuantity,
			title =
			this.title,
			createdAt = this.createdAt,
			price = this.price,
			id = this.id,
			subcategory = this.subcategory,
			category = this.category,
			priceAfterDiscount = this.priceAfterDiscount,
			brand = this.brand,
			slug = this.slug,
			updatedAt = this.updatedAt
		)

}
