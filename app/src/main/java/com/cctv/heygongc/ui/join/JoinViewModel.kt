package com.cctv.heygongc.ui.join

import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.ui.login.LoginService
import com.cctv.heygongc.util.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinViewModel: ViewModel() {

    var radioButton : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox1 : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox2 : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox3 : MutableLiveData<Boolean> = MutableLiveData(false)

    var confirmButton : MutableLiveData<Boolean> = MutableLiveData(false)

    fun clickRadioButton() {
        radioButton.value = !radioButton.value!!
        if (radioButton.value!!) {
            checkBox1.value = true
            checkBox2.value = true
            checkBox3.value = true
        } else {
            checkBox1.value = false
            checkBox2.value = false
            checkBox3.value = false
        }
    }

    fun clickButton() {
        confirmButton.value = !confirmButton.value!!
    }
}