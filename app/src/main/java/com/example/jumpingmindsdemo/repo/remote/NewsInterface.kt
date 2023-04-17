package com.example.jumpingmindsdemo.repo.remote

import com.example.jumpingmindsdemo.repo.remote.data_classes.News
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "0dbea7ea4fdd41a28cded6a02c2060cc"
//https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=0dbea7ea4fdd41a28cded6a02c2060cc

interface NewsInterface {


    @GET("top-headlines?apiKey=$API_KEY")
    fun getTopHeadlines(@Query("country") country : String, @Query("page") page : Int) : Call<News>


}

object NewsService{
    val newsInstance : NewsInterface
    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}