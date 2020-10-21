package com.testapp.testapp_app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeerListResponse(
    @SerializedName("currentPage") var currentPage: Int,
    @SerializedName("numberOfPages") var numberOfPages: Int,
    @SerializedName("data") var data: ArrayList<BeerBean>?
): Parcelable

@Parcelize
data class BeerBean(
    @SerializedName("id") var id: Int,
    @SerializedName("nameDisplay") var name: String,
    @SerializedName("description") var description: String?,
    @SerializedName("foodPairings") var foodPairings: String?,
    @SerializedName("abv") var abv: String?,
    @SerializedName("isRetired") var isRetired: Boolean,
    @SerializedName("style") var style: BeerStyleBean?,
    @SerializedName("labels") var images: ArrayList<PhotoBean>?
): Parcelable

@Parcelize
data class PhotoBean(
    @SerializedName("contentAwareIcon") var icon: String?,
    @SerializedName("contentAwareMedium") var medium: String?,
    @SerializedName("contentAwareLarge") var large: String?
): Parcelable