package com.cctv.heygongc.ui.join

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.ActivityJoinBinding

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
        joinViewModel.radioButton.observe(this) {
            if (it) {
                joinViewModel.checkBox1.value = true
                joinViewModel.checkBox2.value = true
                joinViewModel.checkBox3.value = true
            } else {
                joinViewModel.checkBox1.value = false
                joinViewModel.checkBox2.value = false
                joinViewModel.checkBox3.value = false
            }
        }

        joinViewModel.checkBox1.observe(this) {
            binding.buttonComfirm.isEnabled = it && joinViewModel.checkBox2.value!!
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox2.value!! &&  joinViewModel.checkBox3.value!!
        }

        joinViewModel.checkBox2.observe(this) {
            binding.buttonComfirm.isEnabled = it && joinViewModel.checkBox1.value!!
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox3.value!!
        }

        joinViewModel.checkBox3.observe(this) {
            binding.radioButtonAll.isChecked = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox2.value!!
//            joinViewModel.radioButton.value = it && joinViewModel.checkBox1.value!! &&  joinViewModel.checkBox2.value!!
        }


    }


}