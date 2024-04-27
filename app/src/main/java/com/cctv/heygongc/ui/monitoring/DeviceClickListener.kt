package com.cctv.heygongc.ui.monitoring

import com.cctv.heygongc.data.model.DeviceDetail

interface DeviceClickListener {

    fun onDeviceClick(device: DeviceDetail)
}