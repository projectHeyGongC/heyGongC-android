package com.cctv.heygongc.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.ui.activity.main.ActivityMain
import com.cctv.heygongc.data.remote.model.SoundData
import com.cctv.heygongc.databinding.RecyclerviewSoundBinding

class SoundRecyclerViewAdapter(items: List<SoundData>) : RecyclerView.Adapter<SoundRecyclerViewAdapter.ViewHolder>() {
    var items = items
    inner class ViewHolder(private val binding: RecyclerviewSoundBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SoundData, deviceExist: Boolean) {
            binding.textViewWhere.text = item.where
            binding.textViewMsg.text = item.msg

            if (deviceExist) {
                binding.linearLayoutContainer.setOnClickListener {
                    // 클릭
                    Log.e("클릭","${item.where}")
                    ActivityMain.showFragment(ActivityMain.FRAGMENT_SOUND)
                }
            }
        }
    }

    // recyclerview_sound.xml 파일 이름으로 RecyclerviewSoundBinding이 자동 생성 됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(RecyclerviewSoundBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = 3

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position <= items.size-1) {
            holder.bind(items[position], true)
        } else {
            holder.bind(SoundData("","기기를 추가하여 리포트를 받아보세요"), false)
        }

    }
}