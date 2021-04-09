package com.example.bountyhunter.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
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
import com.example.bountyhunter.bean.beannew.PrivateMessageItem
import com.example.bountyhunter.utils.TimeUtil

/**
 *@author zhangzhe
 *@date 2021/4/7
 *@description
 */

class MessageAdapter(
    private val onClick: ((PrivateMessageItem) -> Unit),
    private val onLongClick: ((View, PrivateMessageItem) -> Unit)
) :
    ListAdapter<PrivateMessageItem, MessageAdapter.PrivateMessageItemViewHolder>(DiffCallback()) {


    inner class PrivateMessageItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrivateMessageItemViewHolder {
        return PrivateMessageItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_message_header, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PrivateMessageItemViewHolder, position: Int) {
        if (getItem(position).status == 0) {
            holder.itemView.findViewById<TextView>(R.id.tv_message_content)
                .setTextColor(Color.parseColor("#FF6E3A"))
        } else {
            holder.itemView.findViewById<TextView>(R.id.tv_message_content)
                .setTextColor(Color.BLACK)
        }
        holder.itemView.findViewById<TextView>(R.id.tv_message_content).text =
            getItem(position).messageContent
        holder.itemView.findViewById<TextView>(R.id.tv_time).text =
            TimeUtil.getChatTimeStr(getItem(position).sendTime)
        holder.itemView.setOnClickListener {
            if (position < currentList.size) {
                onClick.invoke(getItem(position))
            }
        }
        holder.itemView.setOnLongClickListener {
            if (position < currentList.size) {
                onLongClick.invoke(it, getItem(position))
            }
            true
        }

        Glide.with(holder.itemView.context).load(getItem(position).friendUser?.avatarUrl)
            .into(holder.itemView.findViewById<ImageView>(R.id.iv_user_avatar))
        holder.itemView.findViewById<TextView>(R.id.tv_user_nickname).text =
            getItem(position).friendUser?.nickname

    }

    private class DiffCallback : DiffUtil.ItemCallback<PrivateMessageItem>() {
        override fun areItemsTheSame(
            oldItem: PrivateMessageItem,
            newItem: PrivateMessageItem
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: PrivateMessageItem,
            newItem: PrivateMessageItem
        ): Boolean {
            return false
        }
    }

}