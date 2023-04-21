package com.example.jumpingmindsdemo.repo.local.favorites

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.jumpingmindsdemo.repo.local.favorites.Favorites

/**
 * FavoritesDAO for Favorites Articles.
 */
@Dao
interface FavoritesDAO {

    @Insert
    suspend fun insertArticle(article: Favorites)

    @Delete
    suspend fun deleteArticle(article: Favorites)

    @Query("SELECT * FROM favorites")
    fun getArticles(): LiveData<MutableList<Favorites>>

}