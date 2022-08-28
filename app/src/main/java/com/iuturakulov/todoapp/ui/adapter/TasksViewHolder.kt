package com.iuturakulov.todoapp.ui.adapter

import android.content.Context
import android.text.format.DateFormat
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.iuturakulov.todoapp.R
import com.iuturakulov.todoapp.databinding.ItemTaskBinding
import com.iuturakulov.todoapp.model.TodoItem
import java.util.*

class TasksViewHolder(
    private val binding: ItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context, task: TodoItem, onTaskClick: (Long, View) -> Unit) {
        binding.titleTextView.text = task.title.value
        val dateString = DateFormat.format("dd/MM/yyyy HH:mm", Date(task.created))
        binding.dateTextView.text = dateString
        binding.priorityTextView.text = task.taskPriority.value.toString()
        itemView.transitionName = context.resources.getString(R.string.shared_element) + task.id
        setClickListener(onTaskClick, task)
    }

    private fun setClickListener(
        onTaskClick: (Long, View) -> Unit,
        task: TodoItem
    ) {
        itemView.setOnClickListener { onTaskClick(task.id, itemView) }
    }
}