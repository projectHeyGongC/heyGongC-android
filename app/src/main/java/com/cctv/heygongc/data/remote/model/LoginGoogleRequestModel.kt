package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginGoogleRequestModel(
    @SerializedName("grant_type")
    private val grant_type: String,
    @SerializedName("client_id")
    private val client_id: String,
    @SerializedName("client_secret")
    private val client_secret: String,
    @SerializedName("code")
    private val code: String
)