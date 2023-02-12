package com.mument_android.app

import android.content.Intent
import androidx.core.view.ContentInfoCompat.Flags
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mument_android.app.presentation.ui.main.MainActivity

class FCMMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        //super.onMessageReceived(message)
        sendBroadcast(Intent("NEW_INTENT").apply {
            putExtra("NEW_INTENT", true)
        })
        Notification(this, message.data["title"] ?: "", message.data["body"] ?: "")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}