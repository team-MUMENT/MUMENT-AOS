package com.mument_android.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.mument_android.R
import com.mument_android.home.notify.NotifyActivity

class Notification(
    private val context: Context,
    private val title: String,
    private val body: String
) {
    init {
        val builder = setBuilder()
        createNotificationChannel().notify(1, builder.build())
    }

    private fun setBuilder(): NotificationCompat.Builder {
        val resultPendingIntent = setPendingIntent()
        return with(context) {
            NotificationCompat.Builder(this, getString(R.string.notification_channel))
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(resultPendingIntent)
                .setFullScreenIntent(resultPendingIntent, true)
        }
    }

    private fun setPendingIntent(): PendingIntent {
        val resultIntent = Intent(context, NotifyActivity::class.java).apply {
            putExtra("NEW INTENT", "HI")
        }
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    private fun createNotificationChannel(): NotificationManager {
        val name = context.getString(R.string.notification_channel)
        val descriptionText = "Description"
        val importance = NotificationManager.IMPORTANCE_MAX
        val channel = NotificationChannel(
            context.getString(R.string.notification_channel),
            name,
            importance
        ).apply {
            description = descriptionText
            setShowBadge(true)
            enableVibration(true)
            enableLights(true)
            lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        return notificationManager

    }

}