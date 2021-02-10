package com.godamy.myplayer.common

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val resFun = doOp(2, 3, ::sumFun)
}

fun sumFun(x: Int, y: Int) = x + y

fun doOp(x: Int, y: Int, op: (Int, Int) -> Int) = op(x, y)

//LAMBDAS RECEIVERS
//PARTE 1 CON UN TEXT VIEW
fun TextView.apply2(boddy: TextView.() -> Unit): TextView {
    this.boddy()
    return this
}

fun TextView.run2(boddy: TextView.() -> EditText): EditText {
    return this.boddy()
}

fun TextView.let2(boddy: (TextView) -> EditText): EditText {
    return boddy(this)
}

fun with2(receiver: TextView, boddy: TextView.() -> EditText): EditText {
    return receiver.boddy()
}

fun TextView.also2(boddy: (TextView) -> Unit): TextView {
    boddy(this)
    return this
}

//PARTE 2 GENERICOS DE LA PARTE 1
//scope this
fun <T> T.apply2(boddy: T.() -> Unit): T {
    this.boddy()
    return this
}
//scope this
fun <T, U> T.run2(boddy: T.() -> U): U {
    return this.boddy()
}

//scope it
fun <T, U> T.let2(boddy: (T) -> U): U {
    return boddy(this)
}

//scope this
fun <T,U> with2(receiver: T, boddy: T.() -> U): U {
    return receiver.boddy()
}

//scope it
fun <T> T.also2(boddy: (T) -> Unit): T {
    boddy(this)
    return this
}

//Collections
fun testCollections() {
    //List
    val listOfInt = listOf(4,2,5,1)

    var listMutable = mutableListOf(4,2,5,6)

    //SET
    val set = setOf(2,5,8,6)

    //MAP
    val map = mapOf(Pair("a",1), Pair("b",3))
    for ((key, value) in map) {

    }
    //Infix Function
    val infix = mapOf("a" to 1, "b" to 3)

    for(i in 0 until 10) {

    }
    (0 until 10).forEach(::print)


    //asSequence
    //Las sequence se debe usar con las listas cuando estas tienen muchas operaciones y son complejas ya que me dan velocidad
    val sequence = listOfInt
        .asSequence()
        .filter { it % 2 == 0 }
        .map { it.toString() }
        .toList()
}

//Corrutinas
fun testCorrutina(viewGroup: ViewGroup) {
    GlobalScope.launch(Dispatchers.Main) {
        var result = withContext(Dispatchers.IO) { heavyTask() }
        print(result)
    }
}

fun  heavyTask() : String = "Hello"

fun testNulidad() {
    val x : Int? = null
    val y : Long = x?.toLong() ?: 0
}