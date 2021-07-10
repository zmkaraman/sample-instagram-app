package com.example.sampleapp.ui.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.AppUtil
import com.example.sampleapp.network.ApiStatus
import com.example.sampleapp.network.InstagramApi
import com.example.sampleapp.network.model.MediaData
import com.example.sampleapp.network.model.UserMediaData
import com.example.sampleapp.network.model.UserNode
import kotlinx.coroutines.launch
import java.util.logging.Level
import java.util.logging.Logger

class InsGraphViewModel : ViewModel() {

    /*
    private val _userMediaData = MutableLiveData<List<UserMediaData>>()
    val userMediaData: LiveData<List<UserMediaData>>
        get() = _userMediaData*/

    private val _mediaData = MutableLiveData<List<MediaData>>()
    val mediaData: LiveData<List<MediaData>>
        get() = _mediaData

    private val _userNode = MutableLiveData<UserNode>()
    val userNode: LiveData<UserNode>
        get() = _userNode

    private val _navigateToDetailFragment = MutableLiveData<String>()
    val navigateToDetailFragment: LiveData<String>
        get() = _navigateToDetailFragment

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?>
        get() = _errorMessage

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status


    fun getUserProfile() {


        viewModelScope.launch {
            try {

                _status.value = ApiStatus.LOADING
                val userNode = InstagramApi.retrofitGraphService.getUserNode("id,username", AppUtil.getLoginResponse().accessToken)

                Logger.getLogger("InstagramViewModel").log(Level.INFO, "responseBody: $userNode")
                _userNode.value = userNode

                _status.value = ApiStatus.DONE

            } catch (e: Exception) {

                _status.value = ApiStatus.ERROR
                _errorMessage.postValue("Sorry something went wrong! Please try again later!")
            }

        }
    }

    fun getUserMediaData() {

        viewModelScope.launch {
            try {

                _status.value = ApiStatus.LOADING
                val userMedia = InstagramApi.retrofitGraphService.getUserMediaList("id,caption", AppUtil.getLoginResponse().accessToken)

                Logger.getLogger("InstagramViewModel").log(Level.INFO, "responseBody: $userMedia")

                _mediaData.value = userMedia.data

                _status.value = ApiStatus.DONE

            } catch (e: Exception) {

                _status.value = ApiStatus.ERROR
                _errorMessage.postValue("Sorry something went wrong! Please try again later!")
            }

        }
    }


}