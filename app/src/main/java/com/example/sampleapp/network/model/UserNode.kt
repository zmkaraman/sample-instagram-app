package com.example.sampleapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
class UserNode(
        @Json(name = "id") val id:  String,
        @Json(name = "username") val username:  String
): Parcelable
