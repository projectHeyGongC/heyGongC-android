package com.cctv.heygongc.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.data.LoginData
import com.cctv.heygongc.data.TopDateData
import com.cctv.heygongc.databinding.RecyclerviewCellTopDateBinding
import com.cctv.heygongc.databinding.ViewpagerLoginBinding

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
        Log.e("날짜", "${position}, ${items.size-1}")
        holder.bind(items[position], position == items.size-1)
    }
}