package com.cctv.heygongc.data.remote.service

import com.cctv.heygongc.data.remote.apicalladapter.ApiResponse
import com.cctv.heygongc.data.remote.model.AddDeviceRequest
import com.cctv.heygongc.data.remote.model.CommonResponse
import com.cctv.heygongc.data.remote.model.GetAllDeviceResponse
import retrofit2.http.*

interface DeviceService {

    @POST("v1/devices/subscribe")
    fun addDevice(
        @Body addDevice: AddDeviceRequest,
    ): ApiResponse<CommonResponse>

    @GET("v1/devices")
    suspend fun getAllDevices(): ApiResponse<GetAllDeviceResponse>
}