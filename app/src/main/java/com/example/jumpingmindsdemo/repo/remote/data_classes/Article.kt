package com.example.jumpingmindsdemo.repo.remote.data_classes

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.jumpingmindsdemo.R
import com.example.jumpingmindsdemo.utils.AsyncReceiver
import com.example.jumpingmindsdemo.utils.Utils

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
) : java.io.Serializable

@BindingAdapter("android:loadImage")
fun loadImage(imageView : ImageView, url : String?){
    Utils.loadImage(url, imageView, object : AsyncReceiver {
        override fun onSuccess() {

        }

        override fun onFailed(error: Error) {
            imageView.setImageResource(R.drawable.ic_no_image_foreground)
        }

    })
}