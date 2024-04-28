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
import com.cctv.heygongc.databinding.ActivityJoinBinding
import com.cctv.heygongc.util.AlertOneButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityJoin : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    val joinViewModel: JoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("회원가입","진입")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)

        binding.joinViewModel = joinViewModel
        binding.lifecycleOwner = this   // binding과 LoginActivity의 생명주기를 맞춰주기

        setObserve()

    }

    private fun setObserve() {

        // todo : 미란님 mvvm 패턴 방식 보고 적용 하기

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


        joinViewModel.flagGoogleSignup.observe(this) {
            when(it) {
                0 -> {  // 회원가입 성공
                    startActivity(Intent(this@ActivityJoin, ActivityMain::class.java))
                    finish()
                }
                1 -> {
                    var alertOneButton = AlertOneButton(this@ActivityJoin, "", "회원가입에 실패하였습니다\nA09","확인",null)
                    alertOneButton.show()
                }
                2 -> {
                    var alertOneButton = AlertOneButton(this@ActivityJoin, "", "회원가입에 실패하였습니다\nA010","확인",null)
                    alertOneButton.show()
                }
                3 -> {
                    var alertOneButton = AlertOneButton(this@ActivityJoin, "", "회원가입에 실패하였습니다\nA11","확인",null)
                    alertOneButton.show()
                }
            }

        }

    }




}