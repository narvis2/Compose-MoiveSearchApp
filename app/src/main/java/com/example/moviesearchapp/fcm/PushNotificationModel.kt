package com.example.moviesearchapp.fcm

import com.google.gson.annotations.SerializedName

data class PushNotificationModel(
    @SerializedName("style")
    val style: String,
    @SerializedName("action")
    val action: String,
    @SerializedName("userIdx")
    val userIdx: Long?,
    @SerializedName("content_title")
    val content_title: String,
    @SerializedName("content_text")
    val content_text: String,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("webUrl")
    val webUrl: String?
)