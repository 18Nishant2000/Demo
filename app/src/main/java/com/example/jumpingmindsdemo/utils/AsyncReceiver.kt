package com.example.jumpingmindsdemo.utils

interface AsyncReceiver {

    fun onSuccess()

    fun onFailed(error: Error)

}