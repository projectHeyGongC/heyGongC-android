package com.cctv.heygongc.ui.monitoring

import com.cctv.heygongc.ui.model.DeviceStatus

interface DeviceClickListener {

    fun onDeviceClick(device: DeviceStatus)
}