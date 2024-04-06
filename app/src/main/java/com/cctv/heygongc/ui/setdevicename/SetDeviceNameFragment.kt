package com.cctv.heygongc.ui.setdevicename

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.FragmentSetDeviceNameBinding
import com.cctv.heygongc.ui.fragment.MonitoringFragment

class SetDeviceNameFragment : Fragment() {

    private var _binding: FragmentSetDeviceNameBinding? = null
    val binding: FragmentSetDeviceNameBinding = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivClose.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentHost, MonitoringFragment()).addToBackStack(null).commit()

        }
        isNameSet()

        binding.btnAddDevice.setOnClickListener {
            if (isNameSet()) {
                //TODO()edittext 입력값 가지고 set device API 호출
                arguments.let {
                    val deviceInfo = it?.getString("deviceInfo")
                }
            } else {
                return@setOnClickListener
            }
        }

        binding.deviceNameInput.doAfterTextChanged {
            isNameSet()
        }
    }

    private fun isNameSet(): Boolean {
        binding.btnAddDevice.isEnabled = !binding.deviceNameInput.text.isNullOrEmpty()
        return binding.btnAddDevice.isEnabled
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}