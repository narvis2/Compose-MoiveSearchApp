package com.example.domain.model

import org.joda.time.DateTime

data class MovieInfoModel(
    val id: Long = 0L,
    val title: String,
    val link: String,
    val image: String,
    val subtitle: String,
    val pubDate: String,
    val director: String,
    val actor: String,
    val userRating: Float,
    val insertDateTime: DateTime = DateTime.now(),
    val updateTime: DateTime? = null,
    var isSelected: Boolean = false
) {
    val rating = userRating / 2
}
