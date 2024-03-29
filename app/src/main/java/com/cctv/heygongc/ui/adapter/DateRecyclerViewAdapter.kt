package com.cctv.heygongc.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.data.remote.model.TopDateData
import com.cctv.heygongc.databinding.RecyclerviewCellTopDateBinding

class DateRecyclerViewAdapter(items: List<TopDateData>) : RecyclerView.Adapter<DateRecyclerViewAdapter.ViewHolder>() {
    var items = items
    inner class ViewHolder(private val binding: RecyclerviewCellTopDateBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TopDateData, today: Boolean) {
            binding.textViewDate.text = "${item.date}"
            binding.textViewDays.text = item.day

            if (today) {
                binding.textViewDate.setTextColor(Color.parseColor("#006877"))
                binding.textViewDays.setTextColor(Color.parseColor("#006877"))
            } else {
                binding.textViewDate.setTextColor(Color.parseColor("#000000"))
                binding.textViewDays.setTextColor(Color.parseColor("#000000"))
            }

            binding.container.setOnClickListener {
                // 클릭
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(RecyclerviewCellTopDateBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position == items.size-1)
    }
}