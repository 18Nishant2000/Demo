package com.example.jumpingmindsdemo.utils

/**
 * Callback for async tasks
 */
interface AsyncReceiver {
    fun onSuccess()
    fun onFailed(error: Error)
}