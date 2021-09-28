package com.example.taskmanager.ui.homeScreen

import android.widget.ImageView
import android.widget.TextView
import com.example.taskmanager.domain.model.Task
import androidx.databinding.BindingAdapter
import com.bumptech.glide.RequestManager
import com.example.taskmanager.common.Resource
import com.example.taskmanager.domain.model.Weather
import com.example.taskmanager.util.toReadableString
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

@BindingAdapter("weatherTemp","format")
fun TextView.setWeatherTemp(resource: Resource<Weather>, format:String){
    if(resource is Resource.Success){
        resource.data?.let {
            text = String.format(Locale.getDefault(),format,resource.data.temperature)
        }

    }
}

@BindingAdapter("weatherIcon","glide")
fun ImageView.setWeatherIcon(resource: Resource<Weather>, glide:RequestManager){
    if(resource is Resource.Success){
        resource.data?.let {
            val iconUrl = "https://openweathermap.org/img/wn/${resource.data.icon}@2x.png"
            glide.load(iconUrl).into(this)
        }

    }
}
