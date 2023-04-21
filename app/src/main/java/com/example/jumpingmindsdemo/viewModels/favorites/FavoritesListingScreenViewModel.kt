package com.example.jumpingmindsdemo.viewModels.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jumpingmindsdemo.repo.FavoriteArticlesRepository
import com.example.jumpingmindsdemo.repo.local.favorites.Favorites
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * View Model for FavoritesListingScreen.
 */
class FavoritesListingScreenViewModel(private val favoriteArticlesRepository: FavoriteArticlesRepository) :
    ViewModel() {

    fun getFavoritesArticles(): LiveData<MutableList<Favorites>> {
        return favoriteArticlesRepository.getFavoritesArticles()
    }

    fun deleteFavoriteArticle(articleItem: Favorites) {
        GlobalScope.launch {
            favoriteArticlesRepository.deleteFavoriteArticle(articleItem)
        }
    }

}