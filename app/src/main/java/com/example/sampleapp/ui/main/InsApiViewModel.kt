package com.example.sampleapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.AppUtil
import com.example.sampleapp.AppUtil.CLIENT_ID
import com.example.sampleapp.AppUtil.CLIENT_SECRET
import com.example.sampleapp.AppUtil.REDIRECT_URI
import com.example.sampleapp.network.ApiStatus
import com.example.sampleapp.network.InstagramApi
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger

class InsApiViewModel : ViewModel() {

    private val _navigateToMasterFragment = MutableLiveData<Boolean>()
    val navigateToMasterFragment: LiveData<Boolean>
        get() = _navigateToMasterFragment

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status



    fun getAuthToken(code: String) {

        viewModelScope.launch {
            try {

                _status.value = ApiStatus.LOADING
                val loginResponse = InstagramApi.retrofitService.getAuthTokenWithCode(
                    CLIENT_ID, CLIENT_SECRET, GRANT_TYPE,
                    REDIRECT_URI, code
                )
                Logger.getLogger("InstagramViewModel").log(Level.INFO, "responseBody: $loginResponse")

                AppUtil.setLoginResponse(loginResponse)
                _navigateToMasterFragment.value = true
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _navigateToMasterFragment.value = false
                _errorMessage.postValue(ERROR_MSG)
                _status.value = ApiStatus.ERROR

            }

        }
    }

    companion object {
        const val ERROR_MSG = "Sorry something went wrong! Please try again later!"
        const val GRANT_TYPE = "authorization_code"
    }
}