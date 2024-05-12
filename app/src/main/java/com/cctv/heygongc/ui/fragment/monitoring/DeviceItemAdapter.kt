package com.cctv.heygongc.ui.fragment.monitoring

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.data.model.DeviceDetail
import com.cctv.heygongc.databinding.ItemMonitoringDeviceBinding

class DeviceItemAdapter(private val clickListener: DeviceClickListener) :
    ListAdapter<DeviceDetail, DeviceItemAdapter.DeviceStatusViewHolder>(DeviceDetailDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceStatusViewHolder {
        return DeviceStatusViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DeviceStatusViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class DeviceStatusViewHolder(private val binding: ItemMonitoringDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DeviceDetail, clickListener: DeviceClickListener) {
            //TODO(아래 connectStatus, soundSensingStatus 값 true로 오는지 확인)
            //TODO(onClickListener, 카메라 끄고 켜기 + 설정 화면으로 가기, 상세 화면으로 이동? 소기 감지 모드 온오프)
            binding.root.isSelected = item.connectStatus == "true"
            if (binding.root.isSelected) {
                binding.tvDeviceStateDescription.text = "집 이미지를 클릭하면 모니터링이 시작됩니다."
                binding.tvTurnOnCamera.isVisible = false
            } else {
                binding.tvDeviceStateDescription.text = "카메라가 꺼져 있습니다."
                binding.tvTurnOnCamera.isVisible = true
            }
            binding.btnSenseSoundMode.isSelected = item.soundSensingStatus == "true"
            binding.tvDeviceName.text = item.deviceName
            binding.tvTemperature.text = item.temperature.toString()
            binding.tvBattery.text = item.battery.toString()
            binding.root.setOnClickListener {
                clickListener.onDeviceClick(item)
            }
        }

        companion object {
            fun from(parent: ViewGroup): DeviceStatusViewHolder {
                return DeviceStatusViewHolder(
                    ItemMonitoringDeviceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    class DeviceDetailDiffCallback : DiffUtil.ItemCallback<DeviceDetail>() {
        override fun areItemsTheSame(oldItem: DeviceDetail, newItem: DeviceDetail): Boolean {
            return oldItem.deviceId == newItem.deviceId
        }

        override fun areContentsTheSame(oldItem: DeviceDetail, newItem: DeviceDetail): Boolean {
            return oldItem == newItem
        }

    }
}