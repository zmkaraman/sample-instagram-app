package com.example.sampleapp.ui.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.AppUtil
import com.example.sampleapp.network.ApiStatus
import com.example.sampleapp.network.InstagramApi
import com.example.sampleapp.network.model.MediaData
import com.example.sampleapp.network.model.UserNode
import com.example.sampleapp.ui.BaseViewModel
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger

class InsGraphViewModel : BaseViewModel() {

    private val _mediaData = MutableLiveData<List<MediaData>>()
    val mediaData: LiveData<List<MediaData>>
        get() = _mediaData

    private val _userNode = MutableLiveData<UserNode>()
    val userNode: LiveData<UserNode>
        get() = _userNode

    fun getUserProfile() {

        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                val userNode = InstagramApi.retrofitGraphService.getUserNode(FIELDS_USER_NODE, AppUtil.getLoginResponse().accessToken)

                Logger.getLogger("InstagramViewModel").log(Level.INFO, "responseBody: $userNode")
                _userNode.value = userNode
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {

                _status.value = ApiStatus.ERROR
                _errorMessage.postValue(ERROR_MSG)
            }
        }
    }

    fun getUserMediaData() {

        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                val userMedia = InstagramApi.retrofitGraphService.getUserMediaList(FIELDS_MEDIA_DATA, AppUtil.getLoginResponse().accessToken)

                Logger.getLogger("InstagramViewModel").log(Level.INFO, "responseBody: $userMedia")

                _mediaData.value = userMedia.data
                _status.value = ApiStatus.DONE

            } catch (e: Exception) {

                _status.value = ApiStatus.ERROR
                _errorMessage.postValue(ERROR_MSG)
            }
        }
    }

}