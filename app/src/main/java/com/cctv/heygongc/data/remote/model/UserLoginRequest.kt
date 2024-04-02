package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserLoginRequest (
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("deviceOs")
    val deviceOs: String,
    @SerializedName("snsType")
    val snsType: String,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("fcmToken")
    val fcmToken: String,
    @SerializedName("ads")
    val ads: Boolean
)