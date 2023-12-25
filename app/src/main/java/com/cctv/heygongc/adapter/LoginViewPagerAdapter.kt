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

class LoginViewPagerAdapter(context: Context, item: ArrayList<LoginDataViewPager>) : RecyclerView.Adapter<LoginViewPagerAdapter.PagerViewHolder>() {
    var item = item
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder(parent)

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.textViewMsg.text = item[position].msg
        holder.imageViewLogin.setImageResource(item[position].image)
//        Glide.with(context).load(item[position].image)
//            .into(holder.imageViewLogin)
    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.viewpager_login, parent, false))  {

        val textViewMsg = itemView.findViewById<TextView>(R.id.textViewMsg)
        val imageViewLogin = itemView.findViewById<ImageView>(R.id.imageViewLogin)
    }
}