package com.cctv.heygongc.ui.setdevicename

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetDeviceNameViewModel @Inject constructor(private val deviceRepository: DeviceRepository) :
    ViewModel() {

    private val _isComplete = MutableStateFlow(true)
    val isComplete: StateFlow<Boolean> = _isComplete
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    fun addDevice(deviceId: String, deviceName: String) {
        viewModelScope.launch {
            deviceRepository.addDevice(
                onComplete = { _isLoading.value = false },
                onError = {
                    _isError.value = true
                }, deviceId, deviceName
            ).collectLatest { it ->
                if (it) _isComplete.value = true
            }
        }
    }
}