package com.mument_android.app

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mument_android.core_dependent.ext.DataStoreManager
import javax.inject.Inject

class FCMMessagingService : FirebaseMessagingService() {


    override fun onCreate() {
        super.onCreate()
    }



    override fun onMessageReceived(message: RemoteMessage) {
        //super.onMessageReceived(message)
        message.notification?.let { notification ->
            Notification(this, notification.title!!, notification.body!!)
        }
        Log.e("MEssage Data hi", message.data.toString())
        Log.e("MEssage Data click_event", message.data["click_event"].toString())
        Log.e("MEssage Data Channel ID", message.notification?.channelId.toString())
        Log.e("MEssage Body", message.notification?.body.toString())
        Log.e("MEssage Title", message.notification?.title.toString())
        Log.e("MEssage Icon", message.notification?.icon.toString())
        Log.e("MEssage Noti", message.notification.toString())
        Log.e("MEssage messageType", message.messageType.toString())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        /*FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task -> 나중에 다른 곳으로 옮길거임
            if (!task.isSuccessful) {  //기기 토큰 얻어오는 코드
                Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = token.toString()
            Log.e("TAG", msg)
        })*/
    }
}