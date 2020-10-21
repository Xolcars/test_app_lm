package com.testapp.testapp_app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  BeerStyleListResponse(
    @SerializedName("data") var data: ArrayList<BeerStyleBean>?
): Parcelable

@Parcelize
data class BeerStyleBean(
    @SerializedName("id") var id: Int,
    @SerializedName("category ") var category : CategoryBeerStyleBean,
    @SerializedName("name") var name: String,
    @SerializedName("description") var description: String?,
    @SerializedName("abvMax") var abvMax: String?,
    @SerializedName("abvMin") var abvMin: String?

): Parcelable

@Parcelize
data class CategoryBeerStyleBean(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String
): Parcelable