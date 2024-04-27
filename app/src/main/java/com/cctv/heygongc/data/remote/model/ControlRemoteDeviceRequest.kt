package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class ControlRemoteDeviceRequest(
    @SerializedName("controlType")
    val controlType: String,
    @SerializedName("controlMode")
    val controlMode: String?,
)