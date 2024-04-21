package com.cctv.heygongc.service

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cctv.heygongc.ActivityMain
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.e("TAG", "onMessageReceived: ${Gson().toJson(message)}")

        var title = "헤이, 공씨"
        var body = "앱을 확인해주세요"
        message.notification?.let {
            title = it.title.toString()
            body = it.body.toString()
        }
        message.data["title"]?.let {
            title = it
        }
        message.data["body"]?.let {
            body = it
        }

        val type = "type"/*when (message.data["type"]) {

        }*/
        val foreignKey = message.data["foreignKey"]?.toInt() ?: 0

        val intent = Intent(this, ActivityMain::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("type", message.data["type"])
        intent.putExtra("foreignKey", foreignKey)
        sendNotification(intent, title, body, type)
    }

    private fun sendNotification(intent: Intent, title: String, body: String, type: String) {
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                this,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        }

        val notificationBuilder = NotificationCompat.Builder(this, type)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            //.setSmallIcon(R.drawable.app_icon)
            .setContentTitle(title)
            .setContentText(body)
            .setShowWhen(true)
            .setAutoCancel(true)
            .setGroup(type)
            //.setColor(ContextCompat.getColor(this, R.color.primary_main))
            .setStyle(NotificationCompat.BigTextStyle().bigText(body)) //
            .setContentIntent(pendingIntent)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(this)
                .notify(System.currentTimeMillis().toInt(), notificationBuilder.build()) //
            NotificationManagerCompat.from(this)
                .notify(getGroupChannelNumber(type), createGroupNotification(type).build()) //
        }

    }

    private fun createGroupNotification(type: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, type)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            //.setSmallIcon(R.drawable.app_icon)
            .setGroup(type)
            .setGroupSummary(true)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
    }

    private fun getGroupChannelNumber(type: String): Int {
        //Type별로 구현
        return 0
    }
}