package com.example.moviesearchapp.fcm

import android.app.NotificationManager
import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import timber.log.Timber

class MovieFirebaseMessagingService : FirebaseMessagingService() {

    private val objectMapper = Gson()

    override fun onNewToken(token: String) {
        Timber.d("Firebase Cloud Message New Token 👉 $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data

        val pushData = PushNotificationModel(
            style = data["style"] ?: "default",
            action = data["action"] ?: "default",
            userIdx = data["userIdx"]?.toLongOrNull(),
            content_title = data["content_title"] ?: "",
            content_text = data["content_text"] ?: "",
            imageUrl = data["imageUrl"] ?: "",
            webUrl = data["webUrl"] ?: ""
        )

        if (pushData.imageUrl.isNullOrEmpty()) {
            FcmWorker.showNotification(
                context = this,
                pushData = pushData,
                notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            )
        } else {
            try {
                scheduleJob(pushData)
            } catch (e: Exception) { }
        }
    }

    private fun scheduleJob(pushData: PushNotificationModel) {
        val inputData =
            Data.Builder().putString("pushData", objectMapper.toJson(pushData)).build()

        val work = OneTimeWorkRequestBuilder<FcmWorker>().setInputData(inputData).build()

        WorkManager.getInstance(this).enqueue(work)
    }
}