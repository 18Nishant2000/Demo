package com.example.jumpingmindsdemo.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jumpingmindsdemo.repo.ArticlesRepository
import com.example.jumpingmindsdemo.repo.remote.NewsService
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.repo.remote.data_classes.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListingScreenViewModel(private val articlesRepository: ArticlesRepository) : ViewModel() {
    fun getArticles() : LiveData<MutableList<Article>>{
        return articlesRepository.articles
    }

}