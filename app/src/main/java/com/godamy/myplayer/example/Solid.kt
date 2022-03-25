@file:Suppress("unused", "UnnecessaryVariable", "NotImplementedDeclaration")
package com.godamy.myplayer.example

/*
* Example use RoomDataBase and apply Solid
* */

class Item

interface DataBase {
    fun requestItem(): List<Item>
}

class RoomDataBase : DataBase {

    override fun requestItem(): List<Item> {
        TODO()
    }
}

class DataRepository(private val dataBase: DataBase) {

    fun requestItem(): List<Item> {
        val items = dataBase.requestItem()
        // business logic
        return items
    }
}
