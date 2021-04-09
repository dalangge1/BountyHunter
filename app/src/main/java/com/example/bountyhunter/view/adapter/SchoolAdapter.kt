package com.example.bountyhunter.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bountyhunter.R
import com.example.bountyhunter.bean.beannew.School

/**
 *@author zhangzhe
 *@date 2021/4/7
 *@description
 */

class SchoolAdapter(private val onClick: ((School) -> Unit)) :
    ListAdapter<School, SchoolAdapter.SchoolViewHolder>(DiffCallback()) {


    inner class SchoolViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        return SchoolViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_school, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(getItem(position).schoolAvatarUrl)
            .into(holder.itemView.findViewById<ImageView>(R.id.iv_school_avatar))
        holder.itemView.findViewById<TextView>(R.id.tv_school_name).text =
            getItem(position).schoolName
        holder.itemView.findViewById<TextView>(R.id.tv_describe).text = getItem(position).statement
        holder.itemView.findViewById<TextView>(R.id.tv_member).text =
            "成员数：${getItem(position).schoolMember}"
        holder.itemView.findViewById<Button>(R.id.btn_select).setOnClickListener {
            onClick.invoke(getItem(position))
        }

    }

    private class DiffCallback : DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.schoolId == newItem.schoolId
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.schoolId == newItem.schoolId
        }
    }

}