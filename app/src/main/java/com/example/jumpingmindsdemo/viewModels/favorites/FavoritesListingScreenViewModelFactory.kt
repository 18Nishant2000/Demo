package com.example.jumpingmindsdemo.viewModels.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jumpingmindsdemo.repo.FavoriteArticlesRepository

/**
 * View Model Factory for FavoritesListingScreenViewModel.
 */
class FavoritesListingScreenViewModelFactory(private val favoriteArticlesRepository: FavoriteArticlesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavoritesListingScreenViewModel(favoriteArticlesRepository) as T
    }
}