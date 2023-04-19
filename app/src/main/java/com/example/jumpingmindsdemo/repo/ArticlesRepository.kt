package com.example.jumpingmindsdemo.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jumpingmindsdemo.repo.local.FavoritesDatabase
import com.example.jumpingmindsdemo.repo.local.NewsDatabase
import com.example.jumpingmindsdemo.repo.remote.NewsService
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.repo.remote.data_classes.News
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticlesRepository(context : Context) {

    private val newsServiceInstance = NewsService.newsInstance
    private val database = NewsDatabase.getNewsDB(context)
    var data = MutableLiveData<MutableList<Article>>()

    fun getNews(){
        val news = newsServiceInstance.getTopHeadlines("in", 1)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                response.body()?.let {

                    //Saving the articles in DB
                    GlobalScope.launch {
                        it.articles.forEach{
                            database.newsDao().insertArticles(com.example.jumpingmindsdemo.repo.local.News(0, it))
                        }
                    }

                    data.value = it.articles.toMutableList()
                    Log.d("Nishant", "onResponse: ")
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {

            }

        })
    }

}