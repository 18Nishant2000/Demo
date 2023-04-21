package com.example.jumpingmindsdemo.repo

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.jumpingmindsdemo.repo.local.articles.NewsDatabase
import com.example.jumpingmindsdemo.repo.remote.NewsService
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.repo.remote.data_classes.News
import com.example.jumpingmindsdemo.utils.NetworkUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository Class for providing data of Articles.
 */
class ArticlesRepository(
    private val newsDatabase: NewsDatabase,
    private val newsService: NewsService,
    private val context: Context
) {

    private val newsLiveData = MutableLiveData<MutableList<Article>>()
    val articles: LiveData<MutableList<Article>>
        get() = newsLiveData

    suspend fun getArticles(page: Int) {
        if (NetworkUtils.isInternetAvailable(context)) {

            val result = newsService.newsInstance.getTopHeadlines("in", page)
            result.enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    response.body()?.let {

                        //Saving the articles in DB
                        GlobalScope.launch {
                            it.articles.forEach {
                                newsDatabase.newsDao().insertArticles(
                                    com.example.jumpingmindsdemo.repo.local.articles.News(
                                        0,
                                        it
                                    )
                                )
                            }
                        }

                        newsLiveData.postValue(it.articles.toMutableList())
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {

                }

            })

        } else {
            val result = newsDatabase.newsDao().getArticles()
            val articlesList = mutableListOf<Article>()
            result.forEach {
                articlesList.add(it.article)
            }
            newsLiveData.postValue(articlesList)
        }
    }

}