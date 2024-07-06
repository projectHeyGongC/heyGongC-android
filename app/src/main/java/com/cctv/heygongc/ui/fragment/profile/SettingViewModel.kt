package com.cctv.heygongc.ui.fragment.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.data.remote.model.LoginGoogleResponseModel
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.data.repository.DeviceRepository
import com.cctv.heygongc.ui.activity.login.LoginRepository
import com.google.android.gms.common.api.ApiException
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

    var flagBackButton: MutableLiveData<Boolean> = MutableLiveData(false)
    var flagLogout: MutableLiveData<Boolean> = MutableLiveData(false)

    fun goBackSettingFragment() {
        flagBackButton.value = true
    }

    fun logout() {
        flagLogout.value = true
    }

    fun deleteAccount() {

    }


}