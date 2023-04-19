package com.example.jumpingmindsdemo.repo.local

import androidx.room.TypeConverter
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article
import com.example.jumpingmindsdemo.repo.remote.data_classes.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.sql.Blob

class Convertors {

    @TypeConverter
    fun toArticle(article:String):Article{
        val type = object : TypeToken<Article>(){}.type
        return Gson().fromJson(article,type)
    }

    @TypeConverter
    fun toArticleJson(article: Article):String{
        val type=object :TypeToken<Article>(){}.type
        return Gson().toJson(article,type)
    }

}