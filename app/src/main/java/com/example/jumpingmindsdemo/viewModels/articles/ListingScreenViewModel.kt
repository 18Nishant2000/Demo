package com.example.jumpingmindsdemo.viewModels.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jumpingmindsdemo.repo.ArticlesRepository
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article

/**
 * View Model Factory for ListingScreen.
 */
class ListingScreenViewModel(private val articlesRepository: ArticlesRepository) : ViewModel() {
    fun getArticles(): LiveData<MutableList<Article>> {
        return articlesRepository.articles
    }

}