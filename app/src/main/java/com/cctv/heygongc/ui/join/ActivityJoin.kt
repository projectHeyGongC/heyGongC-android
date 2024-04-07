package com.cctv.heygongc.ui.join

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.cctv.heygongc.ActivityMain
import com.cctv.heygongc.R
import com.cctv.heygongc.data.local.Common
import com.cctv.heygongc.data.remote.model.UserLoginRequest
import com.cctv.heygongc.data.remote.model.UserLoginResponse
import com.cctv.heygongc.databinding.ActivityJoinBinding
import com.cctv.heygongc.ui.login.LoginService
import com.cctv.heygongc.util.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityJoin : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    val joinViewModel: JoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinViewModel = joinViewModel
        binding.lifecycleOwner = this   // binding과 LoginActivity의 생명주기를 맞춰주기

        setObserve()

    }

    private fun setObserve() {

        //체크박스1
        joinViewModel.checkBox1.observe(this) {
            binding.buttonComfirm.isEnabled = it && joinViewModel.checkBox2.value!! // 필수 체크박스1,2 가 체크되면 버튼 활성화
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox2.value!! &&  joinViewModel.checkBox3.value!!
            joinViewModel.radioButton.value = it && joinViewModel.checkBox2.value!! &&  joinViewModel.checkBox3.value!!
        }

        //체크박스2
        joinViewModel.checkBox2.observe(this) {
            binding.buttonComfirm.isEnabled = it && joinViewModel.checkBox1.value!! // 필수 체크박스1,2 가 체크되면 버튼 활성화
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox3.value!!
            joinViewModel.radioButton.value = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox3.value!!
        }

        //체크박스3
        joinViewModel.checkBox3.observe(this) {
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox2.value!!
            joinViewModel.radioButton.value = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox2.value!!
        }


        joinViewModel.confirmButton.observe(this) {
            when(it) {
                0 -> {  // 회원가입 성공
                    startActivity(Intent(this@ActivityJoin, ActivityMain::class.java))
                    finish()
                }
                1 -> {
                    Toast.makeText(this@ActivityJoin, "회원가입에 실패하였습니다\nA01", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    Toast.makeText(this@ActivityJoin, "회원가입에 실패하였습니다\nA02", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    Toast.makeText(this@ActivityJoin, "회원가입에 실패하였습니다\nA03", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }




}