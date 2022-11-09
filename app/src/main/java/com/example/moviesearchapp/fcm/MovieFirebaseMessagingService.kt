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
        // Worker 에 데이터 보내기 Data.Builder 를 통해 만들고 직렬화하여 보냄
        val inputData =
            Data.Builder().putString("pushData", objectMapper.toJson(pushData)).build()

        // 제약조건 설정 👉 네트워크가 연결되었을 때만 실행
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        /**
         * ✅ Worker Request 만들기
         * ✔️ OneTimeWorkRequestBuilder 👉 반복하지 않을 작업, 즉 한번만 실행할 작업의 요청
         */
        val work = OneTimeWorkRequestBuilder<FcmWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        // Request 대기열에 추가
        WorkManager.getInstance(this).enqueue(work)
    }
}