package com.example.sampleapp

import com.example.sampleapp.network.model.TokenResponse

object AppUtil {

    private lateinit var loginResponse: TokenResponse
    const val CLIENT_ID = "1004234350366384"
    const val CLIENT_SECRET ="d2fa3ec0425681875fffed540602b665"
    const val REDIRECT_URI = "https://sampleapp.com/oauth"
    const val AUTH_PREFIX = "$REDIRECT_URI?code="
    const val OATH_LINK = "oauth/authorize/?client_id="
    const val BASE_URL = "https://api.instagram.com/"
    const val POSTFIX = "&response_type=code&scope=user_profile,user_media"
    const val REDIRECT_PREFIX = "&redirect_uri="


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