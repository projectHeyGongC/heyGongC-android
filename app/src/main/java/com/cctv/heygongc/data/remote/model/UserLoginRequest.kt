package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserLoginRequest (
    @SerializedName("deviceId")
    private val deviceId: String,
    @SerializedName("deviceOs")
    private val deviceOs: String,
    @SerializedName("snsType")
    private val snsType: String,
    @SerializedName("accessToken")
    private val accessToken: String,
    @SerializedName("fcmToken")
    private val fcmToken: String,
    @SerializedName("ads")
    val ads: Boolean
)