package com.example.taskmanager

import java.text.SimpleDateFormat
import java.util.*


fun Date.toReadableString():String =
   SimpleDateFormat("dd MMM HH:mm",Locale.getDefault()).format(this)

