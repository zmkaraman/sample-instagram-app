package com.example.sampleapp.extension



fun String.getCodeFromRedirectUrl(): String {

    return replace("https://sampleapp.com/oauth?code=","").replace("#_","")

}