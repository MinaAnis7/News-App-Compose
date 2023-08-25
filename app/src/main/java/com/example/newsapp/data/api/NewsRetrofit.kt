package com.example.newsapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsRetrofit {
    private val BASE_URL = "https://newsapi.org/"
    private var retrofitInstance:Retrofit? = null

    fun getInstance():Retrofit?
    {
        if(retrofitInstance == null)
        {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        }

        return retrofitInstance
    }
}