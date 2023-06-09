package com.example.jumpingmindsdemo.viewModels.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jumpingmindsdemo.repo.ArticlesRepository

/**
 * View Model Factory for ListingScreenViewModel.
 */
class ListingScreenViewModelFactory(private val articlesRepository: ArticlesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListingScreenViewModel(articlesRepository) as T
    }
}