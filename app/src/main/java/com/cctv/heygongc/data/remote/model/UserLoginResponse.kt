package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse (
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String
)