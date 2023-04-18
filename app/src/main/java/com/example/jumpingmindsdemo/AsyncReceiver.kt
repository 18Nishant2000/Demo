package com.example.jumpingmindsdemo

interface AsyncReceiver {

    fun onSuccess()

    fun onFailed(error: Error)

}