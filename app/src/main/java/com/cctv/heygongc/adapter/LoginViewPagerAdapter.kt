package com.cctv.heygongc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.cctv.heygongc.R
import com.cctv.heygongc.data.LoginDataViewPager
import com.cctv.heygongc.databinding.ViewpagerLoginBinding

class LoginViewPagerAdapter(context: Context, items: ArrayList<LoginDataViewPager>) : RecyclerView.Adapter<LoginViewPagerAdapter.PagerViewHolder>() {
    var items = items
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(ViewpagerLoginBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PagerViewHolder(private val binding: ViewpagerLoginBinding) : RecyclerView.ViewHolder(binding.root)  {

        fun bind(item: LoginDataViewPager) {
            binding.textViewMsg.text = item.msg
            binding.imageViewLogin.setImageResource(item.image)
        }
    }
}