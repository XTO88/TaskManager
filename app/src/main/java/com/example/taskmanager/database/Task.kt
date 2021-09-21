package com.example.taskmanager.database

import com.google.firebase.firestore.DocumentId

data class Task (

    @DocumentId
    var id:String = "",

    var text:String = "",

    var time:Long = System.currentTimeMillis(),

    var completed:Boolean = false
){
    constructor(text: String):this("",text, System.currentTimeMillis(), false)
}