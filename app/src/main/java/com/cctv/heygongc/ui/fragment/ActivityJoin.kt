package com.cctv.heygongc.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.cctv.heygongc.databinding.ActivityJoinBinding

class ActivityJoin : AppCompatActivity() {
    private var mBinding: ActivityJoinBinding? = null
    private val binding get() = mBinding!!
    var flagRadioButtonAll = false
    var flagCheckBoxTerms = false
    var flagCheckBoxInfo = false
    var flagCheckBoxMarketing = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageViewArrowTerms.setOnClickListener {
//            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
        }

        binding.imageViewArrowInfo.setOnClickListener {
//            Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()
        }

        binding.imageViewArrowMarketing.setOnClickListener {
//            Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()
        }

        binding.radioButtonAll.setOnClickListener {
            flagRadioButtonAll = !flagRadioButtonAll
            binding.radioButtonAll.isChecked = flagRadioButtonAll

            if (flagRadioButtonAll) {
                binding.checkBoxTerms.isChecked = true
                binding.checkBoxInfo.isChecked = true
                binding.checkBoxMarketing.isChecked = true
            } else {
                binding.checkBoxTerms.isChecked = false
                binding.checkBoxInfo.isChecked = false
                binding.checkBoxMarketing.isChecked = false
            }
        }

        binding.checkBoxTerms.setOnCheckedChangeListener { compoundButton, b ->
            flagCheckBoxTerms = b
            checkButton()
        }

        binding.checkBoxInfo.setOnCheckedChangeListener { compoundButton, b ->
            flagCheckBoxInfo = b
            checkButton()
        }

        binding.checkBoxMarketing.setOnCheckedChangeListener { compoundButton, b ->
            flagCheckBoxMarketing = b
            checkButton()
        }

    }

    fun checkButton() {
        binding.radioButtonAll.isChecked = flagCheckBoxTerms && flagCheckBoxInfo && flagCheckBoxMarketing
        flagRadioButtonAll = binding.radioButtonAll.isChecked
        binding.buttonComfirm.isEnabled = flagCheckBoxTerms && flagCheckBoxInfo

    }
}