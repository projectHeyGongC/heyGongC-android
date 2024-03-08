package com.cctv.heygongc.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.data.remote.model.SoundData
import com.cctv.heygongc.data.remote.model.TopDateData
import com.cctv.heygongc.databinding.RecyclerviewCellTopDateBinding
import com.cctv.heygongc.databinding.RecyclerviewSoundBinding

class SoundRecyclerViewAdapter(items: List<SoundData>) : RecyclerView.Adapter<SoundRecyclerViewAdapter.ViewHolder>() {
    var items = items
    inner class ViewHolder(private val binding: RecyclerviewSoundBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SoundData, today: Boolean) {
            binding.textViewWhere.text = item.where
            binding.textViewMsg.text = item.msg

            binding.linearLayoutContainer.setOnClickListener {
                // 클릭
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(RecyclerviewSoundBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position == items.size-1)
    }
}