package com.cctv.heygongc.data.repository

import com.cctv.heygongc.data.datasource.DeviceDataSource
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.data.remote.onError
import com.cctv.heygongc.data.remote.onException
import com.cctv.heygongc.data.remote.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import javax.inject.Inject

class DeviceRepository @Inject constructor(
    private val deviceDataSource: DeviceDataSource
) {

    suspend fun addDevice(
        onComplete: () -> Unit,
        onError: () -> Unit,
        deviceId: String, deviceName: String
    ): Flow<Boolean> = flow {
        val response = deviceDataSource.addDevice(deviceId, deviceName)
        response.onSuccess { addDeviceResponse ->
            emit(true)
        }.onError { code, message ->
            onError()
        }.onException {
            onError()
        }
    }.onCompletion {
        onComplete()
    }

    suspend fun getAllDevices(
        onComplete: () -> Unit,
        onError: () -> Unit
    ): Flow<List<DeviceDetail>> = flow {
        val response = deviceDataSource.getAllDevices()
        response.onSuccess { getAllDevices ->
            val returnList = getAllDevices?.devices?.map {
                DeviceDetail(
                    it.deviceId,
                    it.deviceName,
                    it.battery,
                    it.temperature,
                    it.connectStatus,
                    it.soundSensingStatus
                )
            } ?: emptyList()
            emit(returnList)
        }.onError { code, message ->
            onError()
        }.onException {
            onError()
        }
    }.onCompletion {
        onComplete()
    }

    suspend fun controlRemoteDevice(
        onComplete: () -> Unit,
        onError: () -> Unit,
        deviceId: String, controlType: String, controlMode: String?
    ): Flow<Boolean> = flow {
        val response = deviceDataSource.controlRemoteDevice(deviceId, controlType, controlMode)
        response.onSuccess { response ->
            if (response?.msg?.isNullOrEmpty() == true) emit(true)
        }.onError { code, message ->
            onError()
            emit(false)
        }.onException {
            onError()
            emit(false)
        }
    }.onCompletion {
        onComplete()
    }
}
