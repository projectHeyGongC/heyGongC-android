package com.cctv.heygongc.data

data class UserLoginRequest (
    val deviceId: String,
    val deviceOs: String,
    val ads: Boolean?,
    val token: Token
) {

    data class Token(
        val accessToken: String,
        val refreshToken: String
    )
}