package com.example.jumpingmindsdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jumpingmindsdemo.views.ListingScreen

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainerView, ListingScreen())
            commit()
        }

    }




}