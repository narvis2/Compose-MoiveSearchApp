package com.example.moviesearchapp.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviesearchapp.R
import com.example.moviesearchapp.view.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FcmWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    data class NotificationData(
        val uniId: Int,
        val builder: NotificationCompat.Builder,
    )

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val result = kotlin.runCatching {
                inputData.getString("pushData")?.let {
                    if (it.isNotEmpty()) {
                        val pushData = Gson().fromJson(it, PushNotificationModel::class.java)

                        val notificationManager =
                            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                        val notificationData = showNotification(
                            applicationContext,
                            pushData,
                            notificationManager
                        )

                        if (!pushData.imageUrl.isNullOrBlank()) {
                            val futureBitmap = Glide.with(applicationContext)
                                .setDefaultRequestOptions(RequestOptions().timeout(10000))
                                .asBitmap()
                                .load(pushData.imageUrl)
                                .submit()

                            val resource = futureBitmap.get()
                            notificationData.builder.setStyle(
                                NotificationCompat.BigPictureStyle().bigPicture(resource).bigLargeIcon(null)
                            ).setLargeIcon(resource)

                            notificationManager.notify(
                                notificationData.uniId,
                                notificationData.builder.build()
                            )
                        }
                    }
                }
            }

            if (result.isFailure) {
                return@withContext Result.failure()
            }

            return@withContext Result.success()
        }
    }

    companion object {
        fun showNotification(
            context: Context,
            pushData: PushNotificationModel,
            notificationManager: NotificationManager
        ): NotificationData {
            val fcmGroupKey = context.getString(R.string.app_name)
            // RequestCode, Id를 고유값으로 지정하여 알람이 개별 표시되도록 설정
            val uniId: Int = (System.currentTimeMillis() / 1000).toInt()
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("action", pushData.action)
                putExtra("userIdx", pushData.userIdx)
                putExtra("webUrl", pushData.webUrl)
            }

            // Notification 으로 작업을 수행할 때 Intent 가 실행되도록 함 (Activity를 시작하는 Intent)
            val pendingIntent = PendingIntent.getActivity(
                context,
                uniId,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "FCM",
                    "firebase cloud message",
                    NotificationManager.IMPORTANCE_DEFAULT
                )

                notificationManager.createNotificationChannel(channel)
            }

            val resource = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_favorite_24
            )

            val style = if (!pushData.imageUrl.isNullOrEmpty()) {
                NotificationCompat.BigPictureStyle().bigPicture(resource).bigLargeIcon(null)
            } else {
                NotificationCompat.BigTextStyle().bigText(pushData.content_text)
            }

            // 알림에 대한 UI 정보와 작업을 지정한다.
            val builder = NotificationCompat.Builder(context, "FCM")
                .setSmallIcon(R.drawable.ic_favorite_border_24)
                .setVibrate(longArrayOf(100, 200, 300))
                .setContentTitle(pushData.content_title)
                .setContentText(pushData.content_text)
                .setStyle(style)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.ic_favorite_24
                    )
                )
                .setGroup(fcmGroupKey)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)

            val notification = builder.build()

            // 알림 그룹에 대한 UI 정보와 작업 지정
            val summaryNotification = NotificationCompat.Builder(context, "FCM")
                .setSmallIcon(R.drawable.ic_favorite_border_24)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.InboxStyle())
                .setGroup(fcmGroupKey)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.ic_favorite_24
                    )
                )
                .setGroupSummary(true)
                .build()

            NotificationManagerCompat.from(context).apply {
                notificationManager.notify(uniId, notification)
                notificationManager.notify(0, summaryNotification)
            }

            return NotificationData(uniId, builder)
        }
    }
}