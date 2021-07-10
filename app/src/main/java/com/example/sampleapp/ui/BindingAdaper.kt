package com.example.sampleapp.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.sampleapp.R
import com.example.sampleapp.network.ApiStatus

@BindingAdapter("formatUserName")
fun bindUserName(textView: TextView, username: String?) {

    username?.let {
        textView.text = textView.resources.getString(R.string.hello_text, username)
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