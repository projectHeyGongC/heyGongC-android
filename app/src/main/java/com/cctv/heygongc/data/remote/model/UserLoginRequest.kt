package com.cctv.heygongc.data.remote.model

data class UserLoginRequest (
    val deviceId: String,
    val deviceOs: String,
    val snsType: String,
    val accessToken: String,
    val fcmToken: String,
    val ads: Boolean
)