package com.mument_android.app

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class SampleMessagingService :FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
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