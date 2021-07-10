package com.example.sampleapp

import com.example.sampleapp.network.model.TokenResponse

object AppUtil {

    private lateinit var loginResponse: TokenResponse

    fun setLoginResponse(response: TokenResponse) {
        loginResponse = response
    }

    fun resetLoginResponse() {
        loginResponse = TokenResponse("","")
    }


    fun getLoginResponse() : TokenResponse {
        return loginResponse
    }

}