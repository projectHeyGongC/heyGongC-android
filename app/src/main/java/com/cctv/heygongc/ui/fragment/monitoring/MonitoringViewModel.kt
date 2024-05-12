package com.cctv.heygongc.ui.fragment.monitoring

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonitoringViewModel @Inject constructor(private val deviceRepository: DeviceRepository) :
    ViewModel() {

    private val _allDeviceList = MutableStateFlow<List<DeviceDetail>>(emptyList())
    val allDeviceList: StateFlow<List<DeviceDetail>> = _allDeviceList
    private val _isComplete = MutableStateFlow(true)
    val isComplete: StateFlow<Boolean> = _isComplete
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError

    fun getAllDevices() {
        viewModelScope.launch {
            deviceRepository.getAllDevices(
                onComplete = { _isLoading.value = false },
                onError = {
                    _isError.value = true
                }
            ).collectLatest { it ->
                if (it.isNotEmpty()) _allDeviceList.value = it
            }
        }
    }
}