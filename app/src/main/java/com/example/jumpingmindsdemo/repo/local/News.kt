package com.example.jumpingmindsdemo.repo.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val article : Article
)
