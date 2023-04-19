package com.example.jumpingmindsdemo.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jumpingmindsdemo.repo.ArticlesRepository
import com.example.jumpingmindsdemo.repo.remote.NewsService
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.repo.remote.data_classes.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListingScreenViewModel : ViewModel() {

    var data = MutableLiveData<MutableList<Article>>()


    fun getNews(){

        val news = NewsService.newsInstance.getTopHeadlines("in", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                response.body()?.let {
                    Log.d("Nishant", "onResponse: ")
                    data.value = it.articles.toMutableList()
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {

            }

        })
    }

}