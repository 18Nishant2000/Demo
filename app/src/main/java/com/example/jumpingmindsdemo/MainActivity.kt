package com.example.jumpingmindsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.mainContainerView, FavoritesListingScreen())
                    addToBackStack(
                        null
                    )
                    commit()
                }
                return true
            }

            R.id.action_search -> {
                (application as DemoApplication).search.value = true
                return true
            }

        }
        return false
    }


}