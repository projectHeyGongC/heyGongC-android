package com.cctv.heygongc.ui.setdevicename

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.cctv.heygongc.R
import com.cctv.heygongc.databinding.FragmentSetDeviceNameBinding
import com.cctv.heygongc.ui.monitoring.MonitoringFragment
import kotlinx.coroutines.launch

class SetDeviceNameFragment : Fragment() {

    private var _binding: FragmentSetDeviceNameBinding? = null
    val binding: FragmentSetDeviceNameBinding = _binding!!
    private val setDeviceNameViewModel: SetDeviceNameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivClose.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentHost, MonitoringFragment()).addToBackStack(null).commit()

        }
        isNameSet()

        binding.btnAddDevice.setOnClickListener {
            val deviceInfo = arguments?.getString("deviceInfo")
            if (isNameSet() && deviceInfo != null) {
                //TODO(호출 잘 되는 거 확인)
                arguments.let {
                    viewLifecycleOwner.lifecycleScope.launch {
                        setDeviceNameViewModel.addDevice(deviceInfo, binding.deviceNameInput.text.toString())
                    }
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