package com.example.sampleapp.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.sampleapp.R
import com.example.sampleapp.network.ApiStatus

@BindingAdapter("formatUserName")
fun bindUserName(textView: TextView, username: String?) {

    username?.let {
        textView.text = textView.resources.getString(R.string.hello_text, username)
    }

}

@BindingAdapter("formatTimeStamp")
fun bindTimeStamp(textView: TextView, timestamp: String?) {

    timestamp?.let {
        textView.text = textView.resources.getString(R.string.date_title, timestamp)
    }

}

@BindingAdapter("apiStatus")
fun bindStatus(statusImageView: ImageView, apiStatus: ApiStatus?) {

    when(apiStatus) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)

        }
        ApiStatus.DONE, ApiStatus.ERROR -> {
            statusImageView.visibility = View.GONE

        }
    }
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}