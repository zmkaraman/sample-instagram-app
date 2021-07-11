package com.example.sampleapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.network.ApiStatus

open class BaseViewModel  : ViewModel() {

    protected val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    protected val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    companion object {
        const val FIELDS_MEDIA_DATA = "id,caption"
        const val FIELDS_USER_NODE = "id,username"
        const val FIELDS_USER_MEDIA = "id,media_type,media_url,username,timestamp,caption"
        const val ERROR_MSG = "Sorry something went wrong! Please try again later!"
        const val GRANT_TYPE = "authorization_code"
    }

}