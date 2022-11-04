package com.example.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.data.db.converter.DateTimeConverter
import org.joda.time.DateTime

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "movie_title")
    val title: String,
    @ColumnInfo(name = "movie_image")
    val image: String,
    @ColumnInfo(name = "movie_subtitle")
    val subtitle: String,
    @ColumnInfo(name = "movie_pubDate")
    val pubDate: String,
    @ColumnInfo(name = "movie_director")
    val director: String,
    @ColumnInfo(name = "movie_actor")
    val actor: String,
    @ColumnInfo(name = "movie_rating")
    val rating: Float,
    @field:TypeConverters(DateTimeConverter::class)
    @ColumnInfo(name = "movie_insert_date")
    val insertDate: DateTime =  DateTime.now(),
    @field:TypeConverters(DateTimeConverter::class)
    @ColumnInfo(name = "movie_update_date")
    val updateDate: DateTime? = null
)