package com.cctv.heygongc.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cctv.heygongc.ui.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FragmentViewModel @Inject constructor(
    var loginRepository: LoginRepository
): ViewModel(){

    var flagLogout : MutableLiveData<Int> = MutableLiveData(-1)

    fun logout() {
        flagLogout.value = 0    // todo logout 추가하기
    }

}