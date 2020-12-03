package com.godamy.myplayer.model.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieDbClient {

    //TODO create retrofit url
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//  TODO add service to retrofit to make request
    val service = retrofit.create(MediaApiService::class.java)
}