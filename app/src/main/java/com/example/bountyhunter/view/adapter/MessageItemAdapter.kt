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
import com.example.bountyhunter.bean.beannew.PrivateMessageItem
import com.example.bountyhunter.bean.beannew.User
import com.example.bountyhunter.model.Config
import com.example.bountyhunter.utils.TimeUtil

/**
 *@author zhangzhe
 *@date 2021/4/7
 *@description
 */

class MessageItemAdapter(
    private val onClick: ((View, PrivateMessageItem) -> Unit),
    private val avatarClick: ((User) -> Unit)
) :
    ListAdapter<PrivateMessageItem, MessageItemAdapter.PrivateMessageItemViewHolder>(DiffCallback()) {


    inner class PrivateMessageItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).senderId) {
            Config.userId -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PrivateMessageItemViewHolder {
        return PrivateMessageItemViewHolder(
            when (viewType) {
                0 -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_mine, parent, false)
                else -> LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_friend, parent, false)
            }
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PrivateMessageItemViewHolder, position: Int) {

        holder.itemView.findViewById<TextView>(R.id.tv_message_content).text =
            getItem(position).messageContent
        holder.itemView.findViewById<TextView>(R.id.tv_message_time).text =
            TimeUtil.getChatTimeStr(getItem(position).sendTime)
        holder.itemView.setOnLongClickListener {
            if (position < currentList.size) {
                onClick.invoke(it, getItem(position))
            }
            true
        }

        // 如果是自己的
        if (getItem(position).senderId != Config.userId) {
            holder.itemView.findViewById<ImageView>(R.id.iv_message_avatar).setOnClickListener {
                getItem(position).friendUser?.let { it1 -> avatarClick.invoke(it1) }
            }

            Glide.with(holder.itemView.context).load(getItem(position).friendUser?.avatarUrl)
                .into(holder.itemView.findViewById<ImageView>(R.id.iv_message_avatar))
            holder.itemView.findViewById<TextView>(R.id.tv_message_nickname).text =
                getItem(position).friendUser?.nickname
            when (getItem(position).friendUser?.sex) {
                "男" -> holder.itemView.findViewById<ImageView>(R.id.iv_sex)
                    .setImageResource(R.drawable.ic_man)
                "女" -> holder.itemView.findViewById<ImageView>(R.id.iv_sex)
                    .setImageResource(R.drawable.ic_woman)
            }
        } else {
            holder.itemView.findViewById<ImageView>(R.id.iv_message_avatar).setOnClickListener {
                getItem(position).user?.let { it1 -> avatarClick.invoke(it1) }
            }

            Glide.with(holder.itemView.context).load(getItem(position).user?.avatarUrl)
                .into(holder.itemView.findViewById<ImageView>(R.id.iv_message_avatar))
            holder.itemView.findViewById<TextView>(R.id.tv_message_nickname).text =
                getItem(position).user?.nickname
            when (getItem(position).user?.sex) {
                "男" -> holder.itemView.findViewById<ImageView>(R.id.iv_sex)
                    .setImageResource(R.drawable.ic_man)
                "女" -> holder.itemView.findViewById<ImageView>(R.id.iv_sex)
                    .setImageResource(R.drawable.ic_woman)
            }
        }

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