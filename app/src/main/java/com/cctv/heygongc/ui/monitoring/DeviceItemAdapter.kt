package com.cctv.heygongc.ui.monitoring

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.databinding.ItemMonitoringDeviceBinding
import com.cctv.heygongc.ui.model.DeviceStatus

class DeviceItemAdapter(private val clickListener: DeviceClickListener) :
    ListAdapter<DeviceStatus, DeviceItemAdapter.DeviceStatusViewHolder>(DeviceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceStatusViewHolder {
        return DeviceStatusViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DeviceStatusViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class DeviceStatusViewHolder(private val binding: ItemMonitoringDeviceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DeviceStatus, clickListener: DeviceClickListener) {
            //TODO()
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

    class DeviceDiffCallback : DiffUtil.ItemCallback<DeviceStatus>() {
        override fun areItemsTheSame(oldItem: DeviceStatus, newItem: DeviceStatus): Boolean {
            return oldItem.seq == newItem.seq
        }

        override fun areContentsTheSame(oldItem: DeviceStatus, newItem: DeviceStatus): Boolean {
            return oldItem == newItem
        }

    }
}