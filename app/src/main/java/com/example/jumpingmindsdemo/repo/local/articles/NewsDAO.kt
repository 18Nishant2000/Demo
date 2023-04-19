package com.example.jumpingmindsdemo.repo.local.articles

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.jumpingmindsdemo.repo.local.articles.News

@Dao
interface NewsDAO {

    @Insert
    suspend fun insertArticles(article: News)

    @Delete
    suspend fun deleteArticles(article: News)

    @Query("SELECT * FROM news")
    fun getArticles(): LiveData<MutableList<News>>

}