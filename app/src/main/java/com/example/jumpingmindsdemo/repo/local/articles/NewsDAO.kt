package com.example.jumpingmindsdemo.repo.local.articles

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.jumpingmindsdemo.repo.local.articles.News

@Dao
interface NewsDAO {

    @Insert
    suspend fun insertArticles(article: News)

    @Delete
    suspend fun deleteArticles(article: News)

    @Query("SELECT * FROM news")
    fun getArticles(): MutableList<News>

}