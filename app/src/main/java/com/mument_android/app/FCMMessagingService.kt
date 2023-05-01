package com.mument_android.app

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mument_android.core_dependent.ext.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FCMMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var dataStoreManager: DataStoreManager

    private val exceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            if (throwable is Exception) {  // Error 인지 Exception 인지 판단 (throwable 은 error, exception 이 상속받고 있기 때문)
                Timber.e("Error $throwable")
            }
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        //super.onMessageReceived(message)
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            dataStoreManager.writeIsNotifyExist(true)
        }
        Notification(this, message.data["title"] ?: "", message.data["body"] ?: "")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}