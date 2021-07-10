package com.example.sampleapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
class UserMedia(
        @Json(name = "id") val id:  String,
        @Json(name = "media_type") val mediaType:  String,
        @Json(name = "media_url") val mediaUrl:  String,
        @Json(name = "username") val username:  String,
        @Json(name = "timestamp") val timestamp:  String
): Parcelable


/*
{
  "id": "17895695668004550",
  "media_type": "IMAGE",
  "media_url": "https://fb-s-b-a.akamaihd.net/...",
  "username": "jayposiris"
  "timestamp": "2017-08-31T18:10:00+0000"
}
 */