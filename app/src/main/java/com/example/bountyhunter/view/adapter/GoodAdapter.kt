package com.example.bountyhunter.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bountyhunter.R
import com.example.bountyhunter.bean.beannew.Good

/**
 *@author zhangzhe
 *@date 2021/4/7
 *@description
 */

class GoodAdapter(private val onClick: ((Good) -> Unit)) :
    ListAdapter<Good, GoodAdapter.GoodViewHolder>(DiffCallback()) {


    inner class GoodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodViewHolder {
        return GoodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_good, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GoodViewHolder, position: Int) {

        if (getItem(position).getUserId.isBlank()) {
            holder.itemView.findViewById<TextView>(R.id.tv_is_get).visibility = View.GONE
        } else {
            holder.itemView.findViewById<TextView>(R.id.tv_is_get).visibility = View.VISIBLE
        }
        Glide.with(holder.itemView.context).load(getItem(position).picUrl)
            .into(holder.itemView.findViewById<ImageView>(R.id.iv_good))
        holder.itemView.findViewById<TextView>(R.id.tv_good_name).text =
            getItem(position).goodName
        holder.itemView.findViewById<TextView>(R.id.tv_price).text =
            getItem(position).price.toString()
        holder.itemView.setOnClickListener {
            onClick.invoke(getItem(position))
        }

        Glide.with(holder.itemView.context).load(getItem(position).submitUser?.avatarUrl)
            .into(holder.itemView.findViewById<ImageView>(R.id.iv_user_avatar))
        holder.itemView.findViewById<TextView>(R.id.tv_user_nickname).text =
            getItem(position).submitUser?.nickname

    }

    private class DiffCallback : DiffUtil.ItemCallback<Good>() {
        override fun areItemsTheSame(oldItem: Good, newItem: Good): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: Good, newItem: Good): Boolean {
            return false
        }
    }

}