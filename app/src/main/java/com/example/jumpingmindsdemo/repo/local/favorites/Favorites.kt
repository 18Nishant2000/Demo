package com.example.jumpingmindsdemo.repo.local.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jumpingmindsdemo.repo.remote.data_classes.Article

@Entity(tableName = "favorites")
data class Favorites(
    @PrimaryKey
    val id: String,
    val article: Article
)
