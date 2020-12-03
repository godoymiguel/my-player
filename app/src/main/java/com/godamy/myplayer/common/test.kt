package com.godamy.myplayer.common

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