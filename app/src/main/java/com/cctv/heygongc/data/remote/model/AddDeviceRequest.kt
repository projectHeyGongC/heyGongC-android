package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class AddDeviceRequest (
    @SerializedName("deviceId")
    val deviceId: String,
    @SerializedName("deviceName")
    val deviceName: String,
)