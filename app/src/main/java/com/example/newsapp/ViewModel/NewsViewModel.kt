package com.example.newsapp.ViewModel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newsapp.Models.Article
import com.example.newsapp.data.api.NewsApiInterface
import com.example.newsapp.data.api.NewsRetrofit
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel():ViewModel() {
    var news:List<Article>? = null
    var isArrived:Boolean by mutableStateOf(false)
    var isLoading:Boolean by mutableStateOf(false)
    var isException:Boolean by mutableStateOf(false)

    fun loadNews()
    {
        isLoading = true
        val retrofitInstance = NewsRetrofit.getInstance()?.create(NewsApiInterface::class.java)

        val handler = CoroutineExceptionHandler {
            _, _ ->
            isLoading = false
            isException = true
        }

        CoroutineScope(handler).launch {

            val response = retrofitInstance?.getNews()

            if (response!!.isSuccessful)
            {
                news = response.body()?.articles
                isArrived = true
                isLoading = false
                Log.d("api", "${news}")
            }
            else
            {
                Log.d("api", "${response.errorBody()}")
            }
        }

    }

    fun cancelLoading()
    {
        news = null
        isArrived = false
        isLoading = false
    }

}
