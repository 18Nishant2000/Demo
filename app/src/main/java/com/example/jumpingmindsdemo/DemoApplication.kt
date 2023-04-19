package com.example.jumpingmindsdemo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.jumpingmindsdemo.repo.ArticlesRepository
import com.example.jumpingmindsdemo.repo.local.NewsDatabase
import com.example.jumpingmindsdemo.repo.remote.NewsService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DemoApplication : Application() {

    lateinit var articlesRepository: ArticlesRepository
    lateinit var newsDatabase: NewsDatabase
    lateinit var newsService : NewsService
    var search = MutableLiveData(false)

    override fun onCreate() {
        super.onCreate()

        newsDatabase = NewsDatabase.getNewsDB(applicationContext)
        newsService = NewsService
        articlesRepository = ArticlesRepository(newsDatabase, newsService, applicationContext)

        //For loading the data
        GlobalScope.launch {
            articlesRepository.getArticles(1)
        }
    }

}