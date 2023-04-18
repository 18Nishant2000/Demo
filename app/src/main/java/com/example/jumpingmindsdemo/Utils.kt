package com.example.jumpingmindsdemo

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class Utils {

    companion object{

        fun loadImage(path: String, view: ImageView, callback: AsyncReceiver){
            if(path.isNotEmpty()){
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