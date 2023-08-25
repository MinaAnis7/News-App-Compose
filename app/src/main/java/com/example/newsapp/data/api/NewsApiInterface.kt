package com.example.newsapp.data.api

import com.example.newsapp.Models.NewsModel
import retrofit2.http.GET

interface NewsApiInterface {

    @GET("v2/top-headlines?country=us&apiKey=38b01d9b5aa44951902706ed2947a3c8")
    suspend fun getNews():retrofit2.Response<NewsModel>
}