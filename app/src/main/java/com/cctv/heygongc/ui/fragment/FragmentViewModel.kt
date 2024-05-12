package com.cctv.heygongc.ui.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cctv.heygongc.ui.activity.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentViewModel @Inject constructor(
    var loginRepository: LoginRepository
): ViewModel(){

    var flagLogout : MutableLiveData<Int> = MutableLiveData(-1)
    var flagGoBack : MutableLiveData<Int> = MutableLiveData(-1)

    fun logout() {
        flagLogout.value = 0    // todo logout 추가하기
    }

    fun goBackSettingFragment() {
        Log.e("뒤로가기","진입")
        flagGoBack.value = 0
    }

}
