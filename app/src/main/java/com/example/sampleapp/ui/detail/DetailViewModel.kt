package com.example.sampleapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.AppUtil
import com.example.sampleapp.network.ApiStatus
import com.example.sampleapp.network.InstagramApi
import com.example.sampleapp.network.model.UserMedia
import com.example.sampleapp.ui.BaseViewModel
import kotlinx.coroutines.launch

class DetailViewModel : BaseViewModel() {

    private val _userMedia = MutableLiveData<UserMedia>()
    val userMedia: LiveData<UserMedia>
        get() = _userMedia

    fun getUserMediaData(mediaId: String) {

        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                val userMedia = InstagramApi.retrofitGraphService.getUserMedia(mediaId, FIELDS_USER_MEDIA, AppUtil.getLoginResponse().accessToken)
                _userMedia.value = userMedia
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _errorMessage.postValue(ERROR_MSG)
            }
        }
    }
}