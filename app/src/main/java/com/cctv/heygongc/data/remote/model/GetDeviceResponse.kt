package com.cctv.heygongc.data.remote.model

import com.google.gson.annotations.SerializedName

data class GetAllDeviceResponse(
    @SerializedName("devices") val devices: List<Device>
) {
    data class Device(
        @SerializedName("deviceId") val deviceId: String,
        @SerializedName("deviceName") val deviceName: String,
        @SerializedName("battery") val battery: Int,
        @SerializedName("temperature") val temperature: Int,
        @SerializedName("connectStatus") val connectStatus: String,
        @SerializedName("soundSensingStatus") val soundSensingStatus: String
    )
}