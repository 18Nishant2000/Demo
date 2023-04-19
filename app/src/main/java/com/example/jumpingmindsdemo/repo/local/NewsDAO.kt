package com.example.jumpingmindsdemo.repo.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article

@Dao
interface NewsDAO {

    @Insert
    suspend fun insertArticles(articleList: News)

    /*@Delete
    suspend fun deleteArticles(article: Article)

    @Query("SELECT * FROM news")
    fun getArticles() : LiveData<MutableList<Article>>*/

}