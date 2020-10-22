package com.testapp.testapp_app.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RandomBeerResponse(
    @SerializedName("data") var data: BeerBean?
): Parcelable