package com.cctv.heygongc.ui.monitoring

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cctv.heygongc.databinding.FragmentMonitoringBinding
import com.cctv.heygongc.ui.model.DeviceStatus

class MonitoringFragment : Fragment(), DeviceClickListener {

    private var _binding: FragmentMonitoringBinding? = null
    private val binding get() = _binding!!
    private val deviceItemAdapter = DeviceItemAdapter(this)

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
    }

    private fun setDeviceAdapter() {
        binding.rvMonitoringDevices.adapter = deviceItemAdapter
        //TODO(device list 받아서 adapter에 넘겨주기)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }

    override fun onDeviceClick(device: DeviceStatus) {
        //TODO(디바이스 상세 화면으로 넘어간다! 또는 모니터링 화면으로?)
    }
}