package com.example.taskmanager.ui.taskScreen

import androidx.databinding.BindingAdapter
import com.example.taskmanager.domain.model.Task
import com.example.taskmanager.view.DateTextView
import java.util.*

@BindingAdapter("taskDate")
fun DateTextView.setDate(item: Task?) {
    item?.let {
        val date = Date(item.time)
        setDate(date)
    }
}