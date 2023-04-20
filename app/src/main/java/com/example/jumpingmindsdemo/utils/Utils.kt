package com.example.jumpingmindsdemo.utils

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class Utils {

    companion object {

        fun loadImage(path: String?, view: ImageView, callback: AsyncReceiver) {
            if(path == null) callback.onFailed(Error("Image not loaded"))
            else{
                if (path.isNotEmpty()) {
                    if (path.contains("http")) {
                        Picasso.get().load(path).into(view, object : Callback {
                            override fun onSuccess() {
                                callback.onSuccess()
                            }

                            override fun onError(e: Exception?) {
                                callback.onFailed(Error("Image not loaded"))
                            }
                        })
                    }
                }
            }

        }

    }

}