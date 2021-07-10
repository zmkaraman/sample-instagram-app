package com.example.sampleapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.AppUtil
import com.example.sampleapp.network.ApiStatus
import com.example.sampleapp.network.InstagramApi
import com.example.sampleapp.network.model.UserMedia
import kotlinx.coroutines.launch
import java.util.logging.Logger

class DetailViewModel : ViewModel() {


    private val _userMedia = MutableLiveData<UserMedia>()
    val userMedia: LiveData<UserMedia>
        get() = _userMedia

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status


    fun getUserMediaData(mediaId: String) {

        viewModelScope.launch {
            try {

                _status.value = ApiStatus.LOADING
                val userMedia = InstagramApi.retrofitGraphService.getUserMedia(mediaId, "id,media_type,media_url,username,timestamp,caption", AppUtil.getLoginResponse().accessToken)

                _userMedia.value = userMedia

                _status.value = ApiStatus.DONE

            } catch (e: Exception) {

                _status.value = ApiStatus.ERROR
                _errorMessage.postValue("Sorry something went wrong! Please try again later!")
            }

        }
    }
}