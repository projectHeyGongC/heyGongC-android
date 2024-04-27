package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class CommonResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("msg")val msg: String,
    @SerializedName("data") val `data`: Any?
)