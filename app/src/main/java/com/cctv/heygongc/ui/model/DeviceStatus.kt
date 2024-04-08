package com.cctv.heygongc.ui.model

data class DeviceStatus(
    val seq: Int,
    val isOn: Boolean,
    val name: String,
    val batteryLevel: Int,
    val temperature: Int,
    val isSoundDetectionOn: Boolean
)