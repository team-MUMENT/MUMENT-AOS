package com.mument_android.app

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        //super.onMessageReceived(message)
        Notification(this, message.data["title"] ?: "", message.data["body"] ?: "")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}