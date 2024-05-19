package com.cctv.heygongc.ui.fragment.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.data.repository.DeviceRepository
import com.cctv.heygongc.ui.activity.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val loginRepository: LoginRepository
): ViewModel() {

    var flagBackButton : MutableLiveData<Boolean> = MutableLiveData(false)

    fun goBackSettingFragment() {
        Log.e("뒤로가기","진입")
        flagBackButton.value = true
    }

    fun logout() {
        Log.e("로그아웃","진입")
    }


}