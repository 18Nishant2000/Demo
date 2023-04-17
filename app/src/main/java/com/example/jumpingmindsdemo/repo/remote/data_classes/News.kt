package com.example.jumpingmindsdemo.repo.remote.data_classes

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)