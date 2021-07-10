package com.example.sampleapp.network

import com.example.sampleapp.network.model.TokenResponse
import com.example.sampleapp.network.model.UserMedia
import com.example.sampleapp.network.model.UserMediaData
import com.example.sampleapp.network.model.UserNode
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.instagram.com/"
private const val BASE_GRAPH_URL = "https://graph.instagram.com/"
private const val CLIENT_ID = "1004234350366384"

// Add adapters for Java Date and custom adapter ElectionAdapter (included in project)
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory()) //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //
    .client(InstagramHttpClient.getClient())
    .baseUrl(BASE_URL)
    .build()

private val retrofitGraph = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory()) //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //
        .client(InstagramHttpClient.getClient())
        .baseUrl(BASE_GRAPH_URL)
        .build()


enum class ApiStatus { LOADING, ERROR, DONE }


interface InstagramApiService {


    @FormUrlEncoded
    @POST("oauth/access_token")
    suspend fun getAuthTokenWithCode(@Field("client_id") clientId: String,
                             @Field("client_secret") clientSecret: String,
                             @Field("grant_type")  grantType: String,
                             @Field("redirect_uri") redirectURI: String,
                             @Field("code")  code: String) : TokenResponse



    @GET("me")
    suspend fun getUserNode(@Query("fields") fields: String,
                            @Query("access_token") accessToken: String) : UserNode



    @GET("me/media")
    suspend fun getUserMediaList(@Query("fields") fields: String,
                                 @Query("access_token") accessToken: String) : UserMediaData

    @GET("{fullUrl}")
    suspend fun getUserMedia(@Path(value = "fullUrl", encoded = true) fullUrl: String, @Query("fields") fields: String,
                             @Query("access_token") accessToken: String) : UserMedia


}


object InstagramApi {
    val retrofitService: InstagramApiService by lazy {
        retrofit.create(InstagramApiService::class.java)
    }

    val retrofitGraphService: InstagramApiService by lazy {
        retrofitGraph.create(InstagramApiService::class.java)
    }
}

class InstagramHttpClient: OkHttpClient() {

    companion object {

        fun getClient(): OkHttpClient {
            return Builder()
                .addInterceptor { chain ->
                    val original = chain.request()
                    val url = original
                        .url()
                        .newBuilder()
                        .addQueryParameter("client_id", CLIENT_ID)
                        .build()
                    val request = original
                        .newBuilder()
                        .url(url)
                        .build()
                    chain.proceed(request)
                }
                .build()
        }

    }

}