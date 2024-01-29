package com.cctv.heygongc.ui.login

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cctv.heygongc.data.LoginPagerData
import com.cctv.heygongc.databinding.ViewpagerLoginBinding

class LoginViewPagerAdapter(context: Context, items: ArrayList<LoginPagerData>) : RecyclerView.Adapter<LoginViewPagerAdapter.PagerViewHolder>() {
    var items = items
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(ViewpagerLoginBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PagerViewHolder(private val binding: ViewpagerLoginBinding) : RecyclerView.ViewHolder(binding.root)  {

        fun bind(item: LoginPagerData) {
            binding.textViewMsg.text = item.msg
            binding.imageViewLogin.setImageResource(item.image)
        }
    }
}