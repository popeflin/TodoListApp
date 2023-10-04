package com.dewabrata.todolist.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dewabrata.todolist.R
import com.dewabrata.todolist.uifragment.TodoList
import java.util.*

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {

    var data: MutableList<TodoList> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.listtodo, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TodoList) = with(itemView) {
            // TODO: Bind the data with View
        }
    }
}