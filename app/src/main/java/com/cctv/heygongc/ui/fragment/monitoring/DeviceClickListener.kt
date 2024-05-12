package com.cctv.heygongc.ui.fragment.monitoring

import com.cctv.heygongc.data.model.DeviceDetail

interface DeviceClickListener {

    fun onDeviceClick(device: DeviceDetail)
}