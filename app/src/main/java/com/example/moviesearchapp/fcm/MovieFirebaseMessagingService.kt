package com.example.moviesearchapp.fcm

import android.app.NotificationManager
import android.content.Context
import androidx.work.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import timber.log.Timber

class MovieFirebaseMessagingService : FirebaseMessagingService() {

    private val objectMapper = Gson()

    override fun onNewToken(token: String) {
        Timber.d("Firebase Cloud Message New Token π $token")
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
        // Worker μ λ°μ΄ν° λ³΄λ΄κΈ° Data.Builder λ₯Ό ν΅ν΄ λ§λ€κ³  μ§λ ¬ννμ¬ λ³΄λ
        val inputData =
            Data.Builder().putString("pushData", objectMapper.toJson(pushData)).build()

        // μ μ½μ‘°κ±΄ μ€μ  π λ€νΈμν¬κ° μ°κ²°λμμ λλ§ μ€ν
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        /**
         * β Worker Request λ§λ€κΈ°
         * βοΈ OneTimeWorkRequestBuilder π λ°λ³΅νμ§ μμ μμ, μ¦ νλ²λ§ μ€νν  μμμ μμ²­
         */
        val work = OneTimeWorkRequestBuilder<FcmWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        // Request λκΈ°μ΄μ μΆκ°
        WorkManager.getInstance(this).enqueue(work)
    }
}