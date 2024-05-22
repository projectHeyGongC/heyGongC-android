package com.cctv.heygongc.ui.monitoring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.databinding.FragmentMonitoringBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MonitoringFragment : Fragment(), DeviceClickListener {

    private var _binding: FragmentMonitoringBinding? = null
    private val binding get() = _binding!!
    private val deviceItemAdapter = DeviceItemAdapter(this)
    private val monitoringViewModel: MonitoringViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMonitoringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDeviceAdapter()
        binding.ivAddDeviceBackground.setOnClickListener {
            val action = MonitoringFragmentDirections.actionMonitoringFragmentToScanFragment()
            findNavController().navigate(action)
        }
    }

    private fun setDeviceAdapter() {
        binding.rvMonitoringDevices.adapter = deviceItemAdapter
        //TODO(test device list getAllDevices adapter에 )
        viewLifecycleOwner.lifecycleScope.launch {
            monitoringViewModel.allDeviceList.collectLatest {
                if (it.isNotEmpty()) deviceItemAdapter.submitList(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            monitoringViewModel.getAllDevices()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }

    override fun onDeviceItemClick(device: DeviceDetail) {
        //TODO(디바이스 상세 화면으로 넘어간다! 또는 모니터링 화면으로?)
    }

    override fun onTurnOnDeviceClick(device: DeviceDetail) {
        //TODO(CONNECT STATUS -> 앱이 꺼졌는지 켜졌는지?)
        viewLifecycleOwner.lifecycleScope.launch {
            val controlType =
                if (device.connectStatus == "true") "REMOTE_SHUTDOWN" else "REMOTE_EXECUTION"
            monitoringViewModel.controlRemoteDevice(device.deviceId, controlType, null)
        }
    }
}