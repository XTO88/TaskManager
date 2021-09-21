package com.example.taskmanager.homeScreen

import android.widget.TextView
import com.example.taskmanager.database.Task
import androidx.databinding.BindingAdapter
import com.example.taskmanager.toReadableString
import java.util.*

@BindingAdapter("taskText")
fun TextView.setTaskText(item: Task?) {
    item?.let {
        text = item.text
    }
}

@BindingAdapter("taskTime")
fun TextView.setTaskTime(item: Task?) {
    item?.let {
        text = Date(item.time).toReadableString()
    }
}
