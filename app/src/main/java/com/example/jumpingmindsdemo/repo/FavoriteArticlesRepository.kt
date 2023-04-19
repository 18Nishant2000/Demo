package com.example.jumpingmindsdemo.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.jumpingmindsdemo.repo.local.Favorites
import com.example.jumpingmindsdemo.repo.local.FavoritesDatabase
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteArticlesRepository(context : Context) {

    private val database = FavoritesDatabase.getFavDB(context)


    fun getFavoritesArticles() : LiveData<MutableList<Favorites>> {
        return database.favoritesDao().getArticles()
    }

    fun deleteFavoriteArticle(favArticle : Favorites){
        GlobalScope.launch {
            database.favoritesDao().deleteArticle(favArticle)
        }
    }

}