package com.example.sampleapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.AppUtil
import com.example.sampleapp.AppUtil.CLIENT_ID
import com.example.sampleapp.network.ApiStatus
import com.example.sampleapp.network.InstagramApi
import com.example.sampleapp.network.model.TokenResponse
import com.example.sampleapp.network.model.UserMediaData
import com.example.sampleapp.network.model.UserNode
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger

class InsApiViewModel : ViewModel() {

    private val _userMediaData = MutableLiveData<List<UserMediaData>>()
    val userMediaData: LiveData<List<UserMediaData>>
        get() = _userMediaData

    private val _userNode = MutableLiveData<UserNode>()
    val userNode: LiveData<UserNode>
        get() = _userNode

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

                //TODO MERVE constants dan oku
                _status.value = ApiStatus.LOADING

                val loginResponse = InstagramApi.retrofitService.getAuthTokenWithCode(
                        CLIENT_ID,
                        "d2fa3ec0425681875fffed540602b665", "authorization_code",
                        "https://sampleapp.com/oauth", code
                )
                Logger.getLogger("InstagramViewModel").log(Level.INFO, "responseBody: $loginResponse")

                AppUtil.setLoginResponse(loginResponse)
                //TODO MERVE
                _navigateToMasterFragment.value = true
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _navigateToMasterFragment.value = false
                _errorMessage.postValue(errorMsg)
                _status.value = ApiStatus.ERROR

            }

        }
    }

    companion object {
        const val fields = "id,media_type,media_url,username,timestamp,caption"
        const val errorMsg = "Sorry something went wrong! Please try again later!"
    }
}