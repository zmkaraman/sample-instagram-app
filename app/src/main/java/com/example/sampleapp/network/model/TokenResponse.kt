package com.example.sampleapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class TokenResponse(
    @Json(name = "access_token") val accessToken:  String,
    @Json(name = "user_id") val userId:  String
): Parcelable
