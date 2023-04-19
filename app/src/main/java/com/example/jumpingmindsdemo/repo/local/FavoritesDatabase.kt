package com.example.jumpingmindsdemo.repo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Favorites::class], version = 1)
@TypeConverters(Convertors::class)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoritesDao() : FavoritesDAO

    companion object{
        @Volatile
        private var INSTANCE : FavoritesDatabase? = null
        fun getFavDB(context : Context) : FavoritesDatabase{
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FavoritesDatabase::class.java, "favoritesDB").build()
                }
            }
            return INSTANCE!!
        }
    }


}