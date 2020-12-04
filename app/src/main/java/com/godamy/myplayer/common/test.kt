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

//LAMBDAS
fun lambdasOp() {
    //lambdas
    val sum = { x: Int, y: Int -> x + y }
    val mul = { x: Int, y: Int -> x * y }

    val resSum = doOp(2, 3, sum) //5
    val resMul = doOp(2, 3, mul) //6

    //En este caso se declara la funcion dentro del bloque y no en una val separada
    // Util para callbacks
    val resRes = doOp(2, 3) { x, y ->
        x - y
    } // -1

    //Usaremos una funcion que se comporta como lambdas
    val resFun = doOp(2,3, ::sumFun)
}

fun sumFun (x: Int, y: Int) = x + y

fun doOp(x: Int, y: Int, op: (Int, Int) -> Int) = op(x, y)