package com.cctv.heygongc.ui.login

import com.cctv.heygongc.data.remote.model.LoginGoogleRequestModel
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.SendAccessTokenModel
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface LoginService {
    @POST("oauth2/v4/token")
    fun getAccessToken(
        @Body request: LoginGoogleRequestModel
    ): Call<LoginGoogleResponseModel>

    @POST("v1/users/{snsType}/register")
    @Headers("content-type: application/json")
    fun signup(
        @Path ("snsType") snsType: String,
        @Body loginRequest: UserLoginRequest,
    ): Call<UserLoginRequest>

    @POST("v1/users/login")
    @Headers("content-type: application/json")
    fun login(
        @Body loginRequest: UserLoginRequest,
    ): Call<UserLoginResponse>


    @POST("v1/users/{path}/register")
    @Headers("content-type: application/json")
    fun registerUser(
        @Body accessToken: SendAccessTokenModel
    ): Call<String>

    companion object {

        private val gson = GsonBuilder().setLenient().create()

        fun loginRetrofit(baseUrl: String): LoginService {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(LoginService::class.java)
        }
    }
}