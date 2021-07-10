package com.example.sampleapp.network.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserMediaData(
        @Json(name = "data") val data:  List<MediaData>,
        @Json(name = "paging") val paging:  Paging
): Parcelable


@Parcelize
data class MediaData(
        @Json(name = "id") val id: String,
        @Json(name = "caption") val caption: String?
): Parcelable


@Parcelize
class Paging(
        @Json(name = "cursors") val before: Cursor
): Parcelable

@Parcelize
class Cursor(
        @Json(name = "before") val before: String,
        @Json(name = "after") val after: String
): Parcelable