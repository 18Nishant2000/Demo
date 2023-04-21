package com.example.jumpingmindsdemo.repo.local.articles

import androidx.room.TypeConverter
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Convertor for News.
 */
class NewsConvertor {

    @TypeConverter
    fun toArticle(article: String): Article {
        val type = object : TypeToken<Article>() {}.type
        return Gson().fromJson(article, type)
    }

    @TypeConverter
    fun toArticleJson(article: Article): String {
        val type = object : TypeToken<Article>() {}.type
        return Gson().toJson(article, type)
    }

}