package com.example.data.model.subcategory

import android.os.Parcelable
import com.example.domain.model.Subcategory
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubcategoryDto(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("category")
    val category: String? = null,

    @field:SerializedName("slug")
    val slug: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
) : Parcelable {
    fun toSubCategory(): Subcategory {
        return Subcategory(
            createdAt = createdAt,
            name = name,
            id = id,
            category = category,
            slug = slug,
            updatedAt = updatedAt
        )
    }
}