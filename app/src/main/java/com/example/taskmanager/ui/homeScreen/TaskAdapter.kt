package com.example.taskmanager.ui.homeScreen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.databinding.ItemTaskBinding

class TaskAdapter(private val clickListener: TaskListener) : ListAdapter<Task,
        TaskAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: TaskListener, item: Task) {
            binding.task = item
            binding.clickListener = listener
            binding.executePendingBindings()
            when (item.completed) {
                false -> binding.tvTask.setTextColor(Color.BLACK)
                true -> binding.tvTask.setTextColor(Color.GRAY)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}


class TaskListener(
    val clickListener: (task: Task?) -> Unit,
    val longClickListener: (Task) -> Boolean
) {
    fun onClick(task: Task) = clickListener(task)
    fun onLongClick(task: Task) = longClickListener(task)
}