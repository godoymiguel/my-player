package com.godamy.myplayer.common

import android.view.View
import android.view.ViewGroup
import android.widget.TextView

abstract class Person(name: String, age: Int) {
    var name = name
        get() = "Name: $field"
        set(value) {
            field = value
        }
}

class Developer(name: String, age: Int) : Person(name, age) //herence

fun test() { //function to create object class
    val developer = Developer("Miguel", 31)
}

//Enum
enum class Type { PHOTO, VIDEO }

//when expresion
fun whenExpression(view: View) {
    val result = when (view) {
        is TextView -> view.text.toString()
        is ViewGroup -> view.childCount.toString()
        else -> "No Found"
    }
}