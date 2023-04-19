package com.example.jumpingmindsdemo.repo

import androidx.lifecycle.LiveData
import com.example.jumpingmindsdemo.repo.local.favorites.Favorites
import com.example.jumpingmindsdemo.repo.local.favorites.FavoritesDatabase

class FavoriteArticlesRepository(
    private val favoritesDatabase: FavoritesDatabase
) {

    fun getFavoritesArticles(): LiveData<MutableList<Favorites>> {
        return favoritesDatabase.favoritesDao().getArticles()
    }

    suspend fun deleteFavoriteArticle(favArticle: Favorites) {
        favoritesDatabase.favoritesDao().deleteArticle(favArticle)
    }

}