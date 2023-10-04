package com.dewabrata.todolist.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.dewabrata.todolist.R
import com.dewabrata.todolist.apiservice.model.TodolistItem
import java.util.*

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    var data: MutableList<TodolistItem> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listtodo, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TodolistItem) = with(itemView) {

            itemView.findViewById<TextView>(R.id.txtTugas).text =  item.tugas
            itemView.findViewById<TextView>(R.id.txtDetail).text =  item.detail

            if(item.status == "0") {
                itemView.findViewById<ImageView>(R.id.imgStatus)
                    .setImageResource(R.drawable.baseline_access_time_24)
            }else{
                itemView.findViewById<ImageView>(R.id.imgStatus)
                    .setImageResource(R.drawable.baseline_check_24)
            }
        }
    }
}