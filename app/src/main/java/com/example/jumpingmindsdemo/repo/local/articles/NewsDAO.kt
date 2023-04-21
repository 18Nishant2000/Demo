package com.example.jumpingmindsdemo.repo.local.articles

import androidx.room.*

/**
 * NewsDAO for News.
 */
@Dao
interface NewsDAO {

    @Insert
    suspend fun insertArticles(article: News)

    @Delete
    suspend fun deleteArticles(article: News)

    @Query("SELECT * FROM news")
    fun getArticles(): MutableList<News>

}