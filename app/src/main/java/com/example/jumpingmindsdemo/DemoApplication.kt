package com.example.jumpingmindsdemo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.jumpingmindsdemo.repo.local.FavoritesDatabase

class DemoApplication : Application() {


    init {
        instance = this
    }

    companion object{
        lateinit var instance : DemoApplication
        var search = MutableLiveData(false)
        var favoritesDatabaseInstance : FavoritesDatabase? = null

        fun getFavDBInstance() : FavoritesDatabase{
            if(favoritesDatabaseInstance != null){
                return favoritesDatabaseInstance as FavoritesDatabase
            }
            return Room.databaseBuilder(instance, FavoritesDatabase::class.java, "favDB").build().also {
                favoritesDatabaseInstance = it
            }
        }
    }

}