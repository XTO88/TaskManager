package com.example.taskmanager.util

import java.text.SimpleDateFormat
import java.util.*


fun Date.toReadableString(): String =
    SimpleDateFormat("dd MMM HH:mm", Locale.getDefault()).format(this)