package com.cctv.heygongc.data.datasource

import com.cctv.heygongc.data.remote.apicalladapter.ApiResponse
import com.cctv.heygongc.data.remote.model.AddDeviceRequest
import com.cctv.heygongc.data.remote.model.CommonResponse
import com.cctv.heygongc.data.remote.model.GetAllDeviceResponse
import com.cctv.heygongc.data.remote.service.DeviceService
import javax.inject.Inject

class DeviceDataSource @Inject constructor() {

    @Inject
    lateinit var deviceService: DeviceService

    fun addDevice(deviceId: String, deviceName: String): ApiResponse<CommonResponse> {
        return deviceService.addDevice(AddDeviceRequest(deviceId, deviceName))
    }

    suspend fun getAllDevices(): ApiResponse<GetAllDeviceResponse> {
        return deviceService.getAllDevices()
    }
}