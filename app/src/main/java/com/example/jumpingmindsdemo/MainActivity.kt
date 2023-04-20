package com.example.jumpingmindsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.window.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.splashscreen.SplashScreenViewProvider
import com.example.jumpingmindsdemo.views.favorites.FavoritesListingScreen
import com.example.jumpingmindsdemo.views.articles.ListingScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainerView, ListingScreen())
            commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onStop() {
        (application as DemoApplication).search.value = false
        Log.d("Nishant", "onStop: activity")
        finishAndRemoveTask()
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                (application as DemoApplication).favoriteArticlesRepository.getFavoritesArticles()
                    .observeForever {
                        if (it.isNotEmpty()) {
                            supportFragmentManager.beginTransaction().apply {
                                replace(R.id.mainContainerView, FavoritesListingScreen())
                                addToBackStack(
                                    null
                                )
                                commit()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Favorites are empty",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                return true
            }

            R.id.action_search -> {
                (application as DemoApplication).search.value =
                    !(application as DemoApplication).search.value!!
                return true
            }

        }
        return false
    }


}