package com.example.newsapp.Models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.annotations.SerializedName


data class NewsModel(
    @SerializedName("articles")
    val articles: List<Article>
)

data class Article(
    @SerializedName("title")
    val title: String,
    @SerializedName("urlToImage")
    val image: String,
    @SerializedName("publishedAt")
    val date: String,
)
