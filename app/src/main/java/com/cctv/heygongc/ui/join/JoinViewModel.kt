package com.cctv.heygongc.ui.join

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinViewModel: ViewModel() {

    var radioButton : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox1 : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox2 : MutableLiveData<Boolean> = MutableLiveData(false)
    var checkBox3 : MutableLiveData<Boolean> = MutableLiveData(false)

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
        // 회원가입 api
    }
}