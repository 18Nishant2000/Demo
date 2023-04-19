package com.example.jumpingmindsdemo.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [News::class], version = 1)
@TypeConverters(NewsConvertor::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDAO

    companion object{
        @Volatile
        private var INSTANCE : NewsDatabase? = null
        fun getNewsDB(context : Context) : NewsDatabase{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "newsDB").build()
                }
            }
            return INSTANCE!!
        }
    }
}