package com.example.data.model.subcategory

import android.os.Parcelable
import com.example.data.model.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubcategoryResponse(

    @field:SerializedName("metadata")
    val metadata: Metadata? = null,

    @field:SerializedName("results")
    val results: Int? = null
) : Parcelable, BaseResponse<List<SubcategoryDto?>?>()