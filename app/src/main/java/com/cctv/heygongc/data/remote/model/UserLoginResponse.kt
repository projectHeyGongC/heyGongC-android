package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserLoginResponse (
    @SerializedName("accessToken")
    private val accessToken: String,
    @SerializedName("refreshToken")
    private val refreshToken: String,
    @SerializedName("code")
    private val code: String,
    @SerializedName("message")
    private val message: String
)