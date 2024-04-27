package com.cctv.heygongc.data.model

data class DeviceDetail(
    val deviceId: String,
    val deviceName: String,
    val battery: Int,
    val temperature: Int,
    val connectStatus: String,
    val soundSensingStatus: String
)